package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMerchIntegralMapper;
import com.cykj.pos.domain.BizMerchIntegral;
import com.cykj.pos.service.IBizMerchIntegralService;

import java.util.List;
import java.util.Map;

/**
 * 积分管理Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-19
 */
@Service
public class BizMerchIntegralServiceImpl extends ServiceImpl<BizMerchIntegralMapper, BizMerchIntegral> implements IBizMerchIntegralService {

    @Override
    public List<BizMerchIntegral> queryList(BizMerchIntegral bizMerchIntegral) {
        LambdaQueryWrapper<BizMerchIntegral> lqw = Wrappers.lambdaQuery();
        if (bizMerchIntegral.getMerchId() != null){
            lqw.eq(BizMerchIntegral::getMerchId ,bizMerchIntegral.getMerchId());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getPosCode())){
            lqw.eq(BizMerchIntegral::getPosCode ,bizMerchIntegral.getPosCode());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getIntegralType())){
            lqw.eq(BizMerchIntegral::getIntegralType ,bizMerchIntegral.getIntegralType());
        }
        if (bizMerchIntegral.getValue() != null){
            lqw.eq(BizMerchIntegral::getValue ,bizMerchIntegral.getValue());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getTransType())){
            lqw.eq(BizMerchIntegral::getTransType ,bizMerchIntegral.getTransType());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar1())){
            lqw.eq(BizMerchIntegral::getVar1 ,bizMerchIntegral.getVar1());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar2())){
            lqw.eq(BizMerchIntegral::getVar2 ,bizMerchIntegral.getVar2());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar3())){
            lqw.eq(BizMerchIntegral::getVar3 ,bizMerchIntegral.getVar3());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar4())){
            lqw.eq(BizMerchIntegral::getVar4 ,bizMerchIntegral.getVar4());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar5())){
            lqw.eq(BizMerchIntegral::getVar5 ,bizMerchIntegral.getVar5());
        }
        return this.list(lqw);
    }
}
