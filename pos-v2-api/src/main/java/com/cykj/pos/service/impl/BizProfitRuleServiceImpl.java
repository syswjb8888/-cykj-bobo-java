package com.cykj.pos.service.impl;

import java.util.List;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.pos.domain.BizProfitRule;
import com.cykj.pos.mapper.BizProfitRuleMapper;
import com.cykj.pos.service.IBizProfitRuleService;

/**
 * 分润规则制订Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-08
 */
@Service
public class BizProfitRuleServiceImpl extends ServiceImpl<BizProfitRuleMapper, BizProfitRule> implements IBizProfitRuleService {

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizProfitRule> queryList(BizProfitRule bizProfitRule) {
        LambdaQueryWrapper<BizProfitRule> lqw = Wrappers.lambdaQuery();
        if (bizProfitRule.getProfitMin() != null){
            lqw.eq(BizProfitRule::getProfitMin ,bizProfitRule.getProfitMin());
        }
        if (bizProfitRule.getProfitMax() != null){
            lqw.eq(BizProfitRule::getProfitMax ,bizProfitRule.getProfitMax());
        }
        if (StringUtils.isNotBlank(bizProfitRule.getProfitUnit())){
            lqw.eq(BizProfitRule::getProfitUnit ,bizProfitRule.getProfitUnit());
        }
        if (StringUtils.isNotBlank(bizProfitRule.getProfitType())){
            lqw.eq(BizProfitRule::getProfitType ,bizProfitRule.getProfitType());
        }
        if (bizProfitRule.getProfitAmount() != null){
            lqw.eq(BizProfitRule::getProfitAmount ,bizProfitRule.getProfitAmount());
        }
        if (bizProfitRule.getProfitTop() != null){
            lqw.eq(BizProfitRule::getProfitTop ,bizProfitRule.getProfitTop());
        }
        if (bizProfitRule.getProfitLevel() != null){
            lqw.eq(BizProfitRule::getProfitLevel ,bizProfitRule.getProfitLevel());
        }
        return this.list(lqw);
    }
}
