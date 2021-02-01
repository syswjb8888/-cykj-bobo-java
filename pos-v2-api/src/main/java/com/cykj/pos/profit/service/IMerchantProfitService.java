package com.cykj.pos.profit.service;

import com.cykj.pos.domain.BizProfitRule;

import java.math.BigDecimal;

public interface IMerchantProfitService {

	/**
	 * 计算分润总额
	 * @param year 计算年份
	 * @param month 计算月份
	 * @param merchantId
	 * @return
	 */
	public void caculateProfit(String year,String month,Long merchantId);

	/**
	 * 分润支付
	 * @param settId 分润计算id
	 */
	public String profitPayment(Long settId);
	/**
	 * 交易分润计算
	 * @param monthlyTradeAmount
	 * @return
	 */
	public BigDecimal getTransProfitAmount(BigDecimal monthlyTradeAmount);

	/**
	 * 管理津贴计算
	 * @param monthlyTradeAmount
	 * @param merchCounts 月交易额大于千万的交易量团队数
	 * @return
	 */
	public BigDecimal getManangeAllowance(BigDecimal monthlyTradeAmount,int merchCounts);

	/**
	 * 钻石奖金计算
	 * @param monthlyTradeAmount
	 * @param merchCounts 月交易额大于2千万的交易量团队数
	 * @return
	 */
	public BigDecimal getDiamondReward(BigDecimal monthlyTradeAmount,int merchCounts);

	/**
	 * 皇冠奖计算
	 * @param monthlyTradeAmount
	 * @return
	 */
	public BigDecimal getCrownReward(BigDecimal monthlyTradeAmount);

	/**
	 * 分红奖计算
	 * @param monthlyTradeAmount 月交易额
	 * @param merchCounts 千万级交易量团队数
	 * @return
	 */
	public BigDecimal getBonusReward(BigDecimal monthlyTradeAmount, int merchCounts);

	/**
	 *查询规则单位
	 * @param profitType
	 * @param profitLevel
	 * @return
	 */
	public BigDecimal queryRuleUnit(String profitType,Integer profitLevel);
	/**
	 *根据分润类型查询分润规则
	 * @param profitType
	 * @param condition
	 * @return
	 */
	public BizProfitRule queryOneInRules(String profitType, BigDecimal condition);

	/**
	 *千万级团队数计算
	 * @param merchId
	 * @param level 月交易额等级，千万级：1000000，2千万级：2000000
	 * @return
	 */
	public int getMonthlyTradeAmountTeams(Long merchId,Long level);
}
