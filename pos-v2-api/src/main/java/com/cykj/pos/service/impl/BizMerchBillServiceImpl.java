package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMerchBillMapper;
import com.cykj.pos.domain.BizMerchBill;
import com.cykj.pos.service.IBizMerchBillService;

import java.util.List;
import java.util.Map;

/**
 * 账单信息Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@Service
public class BizMerchBillServiceImpl extends ServiceImpl<BizMerchBillMapper, BizMerchBill> implements IBizMerchBillService {

    @Override
    public List<BizMerchBill> queryList(BizMerchBill bizMerchBill) {
        LambdaQueryWrapper<BizMerchBill> lqw = Wrappers.lambdaQuery();
        if (bizMerchBill.getMerchId() != null){
            lqw.eq(BizMerchBill::getMerchId ,bizMerchBill.getMerchId());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getMerchName())){
            lqw.like(BizMerchBill::getMerchName ,bizMerchBill.getMerchName());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getPosCode())){
            lqw.eq(BizMerchBill::getPosCode ,bizMerchBill.getPosCode());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getPosType())){
            lqw.eq(BizMerchBill::getPosType ,bizMerchBill.getPosType());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getBillType())){
            lqw.eq(BizMerchBill::getBillType ,bizMerchBill.getBillType());
        }
        if (bizMerchBill.getAmount() != null){
            lqw.eq(BizMerchBill::getAmount ,bizMerchBill.getAmount());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getPolicyId())){
            lqw.eq(BizMerchBill::getPolicyId ,bizMerchBill.getPolicyId());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getBillDate())){
            lqw.eq(BizMerchBill::getBillDate ,bizMerchBill.getBillDate());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getVar1())){
            lqw.eq(BizMerchBill::getVar1 ,bizMerchBill.getVar1());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getVar2())){
            lqw.eq(BizMerchBill::getVar2 ,bizMerchBill.getVar2());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getVar3())){
            lqw.eq(BizMerchBill::getVar3 ,bizMerchBill.getVar3());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getVar4())){
            lqw.eq(BizMerchBill::getVar4 ,bizMerchBill.getVar4());
        }
        if (StringUtils.isNotBlank(bizMerchBill.getVar5())){
            lqw.eq(BizMerchBill::getVar5 ,bizMerchBill.getVar5());
        }
        return this.list(lqw);
    }
}
