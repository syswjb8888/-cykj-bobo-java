package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizProfitSettleMapper;
import com.cykj.pos.domain.BizProfitSettle;
import com.cykj.pos.service.IBizProfitSettleService;

import java.util.List;
import java.util.Map;

/**
 * 分润结算信息Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Service
public class BizProfitSettleServiceImpl extends ServiceImpl<BizProfitSettleMapper, BizProfitSettle> implements IBizProfitSettleService {

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizProfitSettle> queryList(BizProfitSettle bizProfitSettle) {
        LambdaQueryWrapper<BizProfitSettle> lqw = Wrappers.lambdaQuery();
        if (bizProfitSettle.getMerchId() != null){
            lqw.eq(BizProfitSettle::getMerchId ,bizProfitSettle.getMerchId());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getMerchCode())){
            lqw.eq(BizProfitSettle::getMerchCode ,bizProfitSettle.getMerchCode());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getMerchName())){
            lqw.like(BizProfitSettle::getMerchName ,bizProfitSettle.getMerchName());
        }
        if (bizProfitSettle.getMonthAmount() != null){
            lqw.eq(BizProfitSettle::getMonthAmount ,bizProfitSettle.getMonthAmount());
        }
        if (bizProfitSettle.getTransProfit() != null){
            lqw.eq(BizProfitSettle::getTransProfit ,bizProfitSettle.getTransProfit());
        }
        if (bizProfitSettle.getManageAllowance() != null){
            lqw.eq(BizProfitSettle::getManageAllowance ,bizProfitSettle.getManageAllowance());
        }
        if (bizProfitSettle.getDiamondReward() != null){
            lqw.eq(BizProfitSettle::getDiamondReward ,bizProfitSettle.getDiamondReward());
        }
        if (bizProfitSettle.getCrownReward() != null){
            lqw.eq(BizProfitSettle::getCrownReward ,bizProfitSettle.getCrownReward());
        }
        if (bizProfitSettle.getBonusReward() != null){
            lqw.eq(BizProfitSettle::getBonusReward ,bizProfitSettle.getBonusReward());
        }
        if (bizProfitSettle.getRewardAmount() != null){
            lqw.eq(BizProfitSettle::getRewardAmount ,bizProfitSettle.getRewardAmount());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getCaculateYear())){
            lqw.eq(BizProfitSettle::getCaculateYear ,bizProfitSettle.getCaculateYear());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getCaculateMonth())){
            lqw.eq(BizProfitSettle::getCaculateMonth ,bizProfitSettle.getCaculateMonth());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getCaculateDay())){
            lqw.eq(BizProfitSettle::getCaculateDay ,bizProfitSettle.getCaculateDay());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getCaculateDate())){
            lqw.eq(BizProfitSettle::getCaculateDate ,bizProfitSettle.getCaculateDate());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getSettleDate())){
            lqw.eq(BizProfitSettle::getSettleDate ,bizProfitSettle.getSettleDate());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getSettleStatus())){
            lqw.eq(BizProfitSettle::getSettleStatus ,bizProfitSettle.getSettleStatus());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getVar1())){
            lqw.eq(BizProfitSettle::getVar1 ,bizProfitSettle.getVar1());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getVar2())){
            lqw.eq(BizProfitSettle::getVar2 ,bizProfitSettle.getVar2());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getVar3())){
            lqw.eq(BizProfitSettle::getVar3 ,bizProfitSettle.getVar3());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getVar4())){
            lqw.eq(BizProfitSettle::getVar4 ,bizProfitSettle.getVar4());
        }
        if (StringUtils.isNotBlank(bizProfitSettle.getVar5())){
            lqw.eq(BizProfitSettle::getVar5 ,bizProfitSettle.getVar5());
        }
        lqw.orderByDesc(BizProfitSettle::getCaculateYear,BizProfitSettle::getCaculateMonth,BizProfitSettle::getCaculateDate);
        return this.list(lqw);
    }
}
