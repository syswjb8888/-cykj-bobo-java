package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizPosPolicyMapper;
import com.cykj.pos.domain.BizPosPolicy;
import com.cykj.pos.service.IBizPosPolicyService;

import java.util.List;
import java.util.Map;

/**
 * 政策管理Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-18
 */
@Service
public class BizPosPolicyServiceImpl extends ServiceImpl<BizPosPolicyMapper, BizPosPolicy> implements IBizPosPolicyService {

    @Override
    public List<BizPosPolicy> queryList(BizPosPolicy bizPosPolicy) {
        LambdaQueryWrapper<BizPosPolicy> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizPosPolicy.getPolicyId())){
            lqw.eq(BizPosPolicy::getPolicyId ,bizPosPolicy.getPolicyId());
        }
        if (bizPosPolicy.getReturnMoney() != null){
            lqw.eq(BizPosPolicy::getReturnMoney ,bizPosPolicy.getReturnMoney());
        }
        if (bizPosPolicy.getTaxation() != null){
            lqw.eq(BizPosPolicy::getTaxation ,bizPosPolicy.getTaxation());
        }
        if (bizPosPolicy.getReturnIntegral() != null){
            lqw.eq(BizPosPolicy::getReturnIntegral ,bizPosPolicy.getReturnIntegral());
        }
        if (bizPosPolicy.getMonths() != null){
            lqw.eq(BizPosPolicy::getMonths ,bizPosPolicy.getMonths());
        }
        if (bizPosPolicy.getAmounts() != null){
            lqw.eq(BizPosPolicy::getAmounts ,bizPosPolicy.getAmounts());
        }
        if (bizPosPolicy.getFrozenAmount() != null){
            lqw.eq(BizPosPolicy::getFrozenAmount ,bizPosPolicy.getFrozenAmount());
        }
        if (StringUtils.isNotBlank(bizPosPolicy.getVar1())){
            lqw.eq(BizPosPolicy::getVar1 ,bizPosPolicy.getVar1());
        }
        if (StringUtils.isNotBlank(bizPosPolicy.getVar2())){
            lqw.eq(BizPosPolicy::getVar2 ,bizPosPolicy.getVar2());
        }
        if (StringUtils.isNotBlank(bizPosPolicy.getVar3())){
            lqw.eq(BizPosPolicy::getVar3 ,bizPosPolicy.getVar3());
        }
        if (StringUtils.isNotBlank(bizPosPolicy.getVar4())){
            lqw.eq(BizPosPolicy::getVar4 ,bizPosPolicy.getVar4());
        }
        if (StringUtils.isNotBlank(bizPosPolicy.getVar5())){
            lqw.eq(BizPosPolicy::getVar5 ,bizPosPolicy.getVar5());
        }
        return this.list(lqw);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BizPosPolicy getPosPolicyByPolicyId(String policyId) {
        LambdaQueryWrapper<BizPosPolicy> posQuery = Wrappers.lambdaQuery();
        posQuery.eq(BizPosPolicy::getPolicyId ,policyId);
        return this.getOne(posQuery);
    }
}
