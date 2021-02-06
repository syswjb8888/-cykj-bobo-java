package com.cykj.pos.profit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.annotation.DataSource;
import com.cykj.common.constant.Constants;
import com.cykj.common.enums.DataSourceType;
import com.cykj.common.utils.StringUtils;
import com.cykj.pos.domain.*;
import com.cykj.pos.enums.bizstatus.MonthEnum;
import com.cykj.pos.service.*;
import com.cykj.pos.util.DESHelperUtil;
import com.cykj.pos.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cykj.pos.profit.service.IMerchantProfitService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class MerchantProfitServiceImpl implements IMerchantProfitService{

	@Autowired
	private IBizProfitRuleService profitRuleService;

	@Autowired
	private IBizProfitSettleService profitSettleService;

	@Autowired
	private IBizMerchantService merchantService;

	@Autowired
	private IBizWalletService walletService;

	@Autowired
	private IBizMerchTransactionsService iBizTransRecordsService;

	@Override
	@Transactional
	public void caculateProfit(String year,String month,Long merchantId) {
		//拼接查询周期（月份），如2021-01或202101，以交易记录中的交易时间格式为准
		String caculatePeriod = year+MonthEnum.getCode(month);
		//1.计算月交易总额（单位：元）
        BigDecimal monthlyTradeAmount = iBizTransRecordsService.getMonthlyTransAmountByMerchId(caculatePeriod,merchantId);
        //2.计算交易分润（单位：元）
        BigDecimal transProfitAmount = this.getTransProfitAmount(monthlyTradeAmount);
        //3.计算管理津贴（单位：元）
        int merchCounts = this.getMonthlyTradeAmountTeams(merchantId,1000000L);
        BigDecimal manageAllowance = this.getManangeAllowance(monthlyTradeAmount,merchCounts);
        //4.计算钻石奖金（单位：元）
        int merchCounts1 = this.getMonthlyTradeAmountTeams(merchantId,2000000L);
        BigDecimal diamondReward = this.getDiamondReward(monthlyTradeAmount,merchCounts1);
        //5.计算皇冠奖
		BigDecimal crownReward = this.getCrownReward(monthlyTradeAmount);
		//6.计算分红奖金
		BigDecimal bonusReward = this.getBonusReward(monthlyTradeAmount,merchCounts);
		//7.保存分润计算结果
		BizMerchant marcher = merchantService.getById(merchantId);

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LambdaQueryWrapper<BizProfitSettle> ruleQuery = Wrappers.lambdaQuery();
		ruleQuery.eq(BizProfitSettle::getMerchId ,marcher.getMerchId());
		ruleQuery.eq(BizProfitSettle::getCaculateYear ,
				          StringUtils.isNotBlank(year)?year:String.valueOf(localDateTime.getYear()));
		ruleQuery.eq(BizProfitSettle::getCaculateMonth ,
				          StringUtils.isNotBlank(month)? month:String.valueOf(localDateTime.getMonth()));
		BizProfitSettle profitSettle = profitSettleService.getOne(ruleQuery);
		if(profitSettle == null){
			profitSettle = new BizProfitSettle();
			profitSettle.setMerchId(marcher.getMerchId())
						.setMerchCode(marcher.getMerchCode())
						.setMerchName(marcher.getMerchName())
						.setTransProfit(transProfitAmount)
						.setManageAllowance(manageAllowance)
						.setDiamondReward(diamondReward)
						.setCrownReward(crownReward)
						.setBonusReward(bonusReward)
						.setMonthAmount(monthlyTradeAmount)
						.setCaculateYear(StringUtils.isNotBlank(year)?year:String.valueOf(localDateTime.getYear()))
						.setCaculateMonth(StringUtils.isNotBlank(month)? month:String.valueOf(localDateTime.getMonth()))
						.setCaculateDay(String.valueOf(localDateTime.getDayOfMonth()))
						.setCaculateDate(localDateTime.format(fmt))
						.setRewardAmount(manageAllowance.add(diamondReward).add(crownReward).add(bonusReward));
		}else{
			profitSettle.setTransProfit(transProfitAmount)
					.setManageAllowance(manageAllowance)
					.setDiamondReward(diamondReward)
					.setCrownReward(crownReward)
					.setBonusReward(bonusReward)
					.setMonthAmount(monthlyTradeAmount)
					.setCaculateDay(String.valueOf(localDateTime.getDayOfMonth()))
					.setCaculateDate(localDateTime.format(fmt))
					.setRewardAmount(manageAllowance.add(diamondReward).add(crownReward).add(bonusReward));
		}
		profitSettleService.saveOrUpdate(profitSettle);
	}

	@Override
	@Transactional
	public String profitPayment(Long settId){
		BizProfitSettle settle = profitSettleService.getById(settId);

		//判断是否已经做过结算支付
		if(settle.getSettleStatus().equals(Constants.SETTLE_PAYED)){
			return Constants.SETTLE_PAYED;
		}
		//开始结算支付
		Long merchId = settle.getMerchId();
		com.cykj.pos.domain.BizMerchant merchant =merchantService.getById(merchId);
		Long userId = merchant.getUserId();
		BigDecimal profitAmount = settle.getTransProfit();
		BigDecimal rewardAmount = settle.getRewardAmount();

		LambdaQueryWrapper<BizWallet> ruleQuery = Wrappers.lambdaQuery();
		ruleQuery.eq(BizWallet::getUserId ,userId);
		BizWallet wallet = walletService.getOne(ruleQuery);

		//结算支付
		if(wallet == null){
			wallet = new BizWallet();
			wallet.setUserId(userId);
		}
        // 代码需要重新设置  加密数据
		String password = wallet.getSecretKey();
		if(password==null || "".equals(password)){
			password = DESHelperUtil.getSecretKey();
		}
		// 需要加密
		String profitAmountStr =  DESHelperUtil.encrypt(password,profitAmount.stripTrailingZeros().toPlainString()); // 避免输出科学计数法
		String rewardAmountStr = DESHelperUtil.encrypt(password,rewardAmount.stripTrailingZeros().toPlainString()); // 避免输出科学计数法
		BigDecimal walletAmount = profitAmount.add(rewardAmount);
		String walletAmountStr = DESHelperUtil.encrypt(password,walletAmount.stripTrailingZeros().toPlainString()); // 避免输出科学计数法
		wallet.setProfitAmount(profitAmountStr);
		wallet.setRewardAmount(rewardAmountStr);
		wallet.setWalletAmount(walletAmountStr);

		walletService.saveOrUpdate(wallet);
		//修改结算支付状态及更新结算支付时间
        settle.setSettleStatus(Constants.SETTLE_PAYED);
		String settleDate = DateUtils.localeDateTime2String(LocalDateTime.now(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        settle.setSettleDate(settleDate);
		profitSettleService.saveOrUpdate(settle);
		return Constants.SETTLE_PAY_SUCCESS;
	}
	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal getTransProfitAmount(@NotNull BigDecimal monthlyTradeAmount){

		BigDecimal ruleUnit = this.queryRuleUnit("0",1);
		//交易量除以规则单位
		BigDecimal convertValue = monthlyTradeAmount.divide(ruleUnit);

		BizProfitRule profitRuleAmount = this.queryOneInRules("0",convertValue);

		return profitRuleAmount == null?BigDecimal.ZERO:profitRuleAmount.getProfitAmount();
	}

	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal getManangeAllowance(@NotNull BigDecimal monthlyTradeAmount,int merchCounts){

		LambdaQueryWrapper<BizProfitRule> ruleQuery = Wrappers.lambdaQuery();
		ruleQuery.eq(BizProfitRule::getProfitType ,"1");
		BizProfitRule manageRule = profitRuleService.getOne(ruleQuery);

		BigDecimal monthlyMin = manageRule.getMonthlyMin();
		BigDecimal profitMin = manageRule.getProfitMin();

		BigDecimal monthTradeAmount = monthlyTradeAmount.divide(BigDecimal.valueOf(Long.valueOf(manageRule.getProfitUnit())));

		if(merchCounts >= profitMin.intValue() && monthTradeAmount.compareTo(monthlyMin) > 0){
			BigDecimal profitAmount = manageRule.getProfitAmount();
			//津贴为月交易额的万分之一
			BigDecimal result = monthlyTradeAmount.multiply(profitAmount);
			BigDecimal profitTop = manageRule.getProfitTop();
			//如果津贴大于封顶则返回封顶值，否则返回实际的津贴值
			return  result.compareTo(profitTop) > 0?profitTop:result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal getDiamondReward(@NotNull BigDecimal monthlyTradeAmount,int merchCounts){
        BizProfitRule manageRule = this.queryOneInRules("2",BigDecimal.valueOf(merchCounts));
        if(manageRule == null)return BigDecimal.ZERO;
		BigDecimal monthlyMin = manageRule.getMonthlyMin();
		BigDecimal monthTradeAmount = monthlyTradeAmount.divide(BigDecimal.valueOf(Long.valueOf(manageRule.getProfitUnit())));
		if(monthTradeAmount.compareTo(monthlyMin) > 0){
			return manageRule.getProfitAmount();
		}
		return BigDecimal.ZERO;
	}


	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal getCrownReward(@NotNull BigDecimal monthlyTradeAmount){
		BigDecimal ruleUnit = this.queryRuleUnit("2",1);
		BigDecimal tradeAmount = monthlyTradeAmount.divide(ruleUnit);
		BizProfitRule manageRule = this.queryOneInRules("2",tradeAmount);
		if(manageRule == null) return BigDecimal.ZERO;
		BigDecimal profitAmount = manageRule.getProfitAmount();
		return profitAmount == null?BigDecimal.ZERO:monthlyTradeAmount.multiply(profitAmount);
	}

	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal getBonusReward(@NotNull BigDecimal monthlyTradeAmount, int merchCounts){

		LambdaQueryWrapper<BizProfitRule> ruleQuery = Wrappers.lambdaQuery();
		ruleQuery.eq(BizProfitRule::getProfitType ,"4");
		ruleQuery.le(BizProfitRule::getMonthlyTomTeams,merchCounts);
		BizProfitRule rule = profitRuleService.getOne(ruleQuery);
        if(rule == null) return BigDecimal.ZERO;

        BigDecimal tradeAmount = monthlyTradeAmount.divide(BigDecimal.valueOf(Long.valueOf(rule.getProfitUnit())));
		BigDecimal profitMin = rule.getProfitMin();
        BigDecimal profitMax = rule.getProfitMax();
		if(tradeAmount.compareTo(profitMin)>=0 && tradeAmount.compareTo(profitMax)<0){
			return rule.getProfitAmount();
		}

		return BigDecimal.ZERO;
	}

	@Override
	@DataSource(DataSourceType.SLAVE)
	public BigDecimal queryRuleUnit(String profitType,Integer profitLevel){
		LambdaQueryWrapper<BizProfitRule> unitQuery = Wrappers.lambdaQuery();
		unitQuery.eq(BizProfitRule::getProfitType ,profitType);
		unitQuery.eq(BizProfitRule::getProfitLevel,profitLevel);
		BizProfitRule profitRuleUnit = profitRuleService.getOne(unitQuery);
		String profitUnit = profitRuleUnit.getProfitUnit();
		return BigDecimal.valueOf(Long.valueOf(profitUnit));
	}
	@Override
	@DataSource(DataSourceType.SLAVE)
	public BizProfitRule queryOneInRules(String profitType, BigDecimal condition){
		LambdaQueryWrapper<BizProfitRule> ruleQuery = Wrappers.lambdaQuery();
		ruleQuery.eq(BizProfitRule::getProfitType ,profitType);
		//交易额大于等下限
		ruleQuery.le(BizProfitRule::getProfitMin,condition);
		//交易额小于上限
		ruleQuery.gt(BizProfitRule::getProfitMax,condition);
		BizProfitRule rule = profitRuleService.getOne(ruleQuery);
		return rule;
	}

	@Override
	public int getMonthlyTradeAmountTeams(Long merchId,Long level){
		int counts = 0;
		List<BizMerchant> childTeams = merchantService.getAllSubNodeAndIncludeSelf(merchId);
		for(BizMerchant merchant: childTeams){
			BigDecimal tradeAmount = iBizTransRecordsService.getMonthlyTransAmountByMerchId(merchant.getMerchId());
			if(tradeAmount != null && tradeAmount.compareTo(BigDecimal.valueOf(level)) > 0){
				counts++;
			}
		}
		return counts;
	}

	/*public static void main(String[] args){
		LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = date.format(fmt).substring(0,6);
        System.out.println("LocalDate转String:"+dateStr);
        System.out.println(date.getYear());
		System.out.println(date.getMonth());
		System.out.println(date.getDayOfYear());
    }*/
}
