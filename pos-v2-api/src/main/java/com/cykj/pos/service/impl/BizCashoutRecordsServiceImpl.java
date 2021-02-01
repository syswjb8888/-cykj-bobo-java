package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizCashoutRecordsMapper;
import com.cykj.pos.domain.BizCashoutRecords;
import com.cykj.pos.service.IBizCashoutRecordsService;

import java.util.List;
import java.util.Map;

/**
 * 提现记录Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
@Service
public class BizCashoutRecordsServiceImpl extends ServiceImpl<BizCashoutRecordsMapper, BizCashoutRecords> implements IBizCashoutRecordsService {

    @Override
    public List<BizCashoutRecords> queryList(BizCashoutRecords bizCashoutRecords) {
        LambdaQueryWrapper<BizCashoutRecords> lqw = Wrappers.lambdaQuery();
        if (bizCashoutRecords.getWalletId() != null){
            lqw.eq(BizCashoutRecords::getWalletId ,bizCashoutRecords.getWalletId());
        }
        if (bizCashoutRecords.getCashoutAmount() != null){
            lqw.eq(BizCashoutRecords::getCashoutAmount ,bizCashoutRecords.getCashoutAmount());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getDealAccount())){
            lqw.eq(BizCashoutRecords::getDealAccount ,bizCashoutRecords.getDealAccount());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getDealBank())){
            lqw.eq(BizCashoutRecords::getDealBank ,bizCashoutRecords.getDealBank());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getPayAccount())){
            lqw.eq(BizCashoutRecords::getPayAccount ,bizCashoutRecords.getPayAccount());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getPayAccountBank())){
            lqw.eq(BizCashoutRecords::getPayAccountBank ,bizCashoutRecords.getPayAccountBank());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getCashoutType())){
            lqw.eq(BizCashoutRecords::getCashoutType ,bizCashoutRecords.getCashoutType());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getCashoutStatus())){
            lqw.eq(BizCashoutRecords::getCashoutStatus ,bizCashoutRecords.getCashoutStatus());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getVar1())){
            lqw.eq(BizCashoutRecords::getVar1 ,bizCashoutRecords.getVar1());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getVar2())){
            lqw.eq(BizCashoutRecords::getVar2 ,bizCashoutRecords.getVar2());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getVar3())){
            lqw.eq(BizCashoutRecords::getVar3 ,bizCashoutRecords.getVar3());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getVar4())){
            lqw.eq(BizCashoutRecords::getVar4 ,bizCashoutRecords.getVar4());
        }
        if (StringUtils.isNotBlank(bizCashoutRecords.getVar5())){
            lqw.eq(BizCashoutRecords::getVar5 ,bizCashoutRecords.getVar5());
        }
        return this.list(lqw);
    }
}
