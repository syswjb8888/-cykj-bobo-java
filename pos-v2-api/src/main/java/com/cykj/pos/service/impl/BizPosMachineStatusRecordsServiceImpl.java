package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizPosMachineStatusRecordsMapper;
import com.cykj.pos.domain.BizPosMachineStatusRecords;
import com.cykj.pos.service.IBizPosMachineStatusRecordsService;

import java.util.List;
import java.util.Map;

/**
 * 终端设备状态记录Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-02
 */
@Service
public class BizPosMachineStatusRecordsServiceImpl extends ServiceImpl<BizPosMachineStatusRecordsMapper, BizPosMachineStatusRecords> implements IBizPosMachineStatusRecordsService {

    @Override
    public List<BizPosMachineStatusRecords> queryList(BizPosMachineStatusRecords bizPosMachineStatusRecords) {
        LambdaQueryWrapper<BizPosMachineStatusRecords> lqw = Wrappers.lambdaQuery();
        if (bizPosMachineStatusRecords.getId() != null){
            lqw.eq(BizPosMachineStatusRecords::getId ,bizPosMachineStatusRecords.getId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getMerchantId())){
            lqw.eq(BizPosMachineStatusRecords::getMerchantId ,bizPosMachineStatusRecords.getMerchantId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getTerminalId())){
            lqw.eq(BizPosMachineStatusRecords::getTerminalId ,bizPosMachineStatusRecords.getTerminalId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getPhoneNo())){
            lqw.eq(BizPosMachineStatusRecords::getPhoneNo ,bizPosMachineStatusRecords.getPhoneNo());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getTime())){
            lqw.eq(BizPosMachineStatusRecords::getTime ,bizPosMachineStatusRecords.getTime());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getIdCardNo())){
            lqw.eq(BizPosMachineStatusRecords::getIdCardNo ,bizPosMachineStatusRecords.getIdCardNo());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getIsActivated())){
            lqw.eq(BizPosMachineStatusRecords::getIsActivated ,bizPosMachineStatusRecords.getIsActivated());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getReceiptType())){
            lqw.eq(BizPosMachineStatusRecords::getReceiptType ,bizPosMachineStatusRecords.getReceiptType());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getDeviceType())){
            lqw.eq(BizPosMachineStatusRecords::getDeviceType ,bizPosMachineStatusRecords.getDeviceType());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getName())){
            lqw.like(BizPosMachineStatusRecords::getName ,bizPosMachineStatusRecords.getName());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getSnCode())){
            lqw.eq(BizPosMachineStatusRecords::getSnCode ,bizPosMachineStatusRecords.getSnCode());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getActiveTime())){
            lqw.eq(BizPosMachineStatusRecords::getActiveTime ,bizPosMachineStatusRecords.getActiveTime());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getIdTxn())){
            lqw.eq(BizPosMachineStatusRecords::getIdTxn ,bizPosMachineStatusRecords.getIdTxn());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getDirectlyOrgId())){
            lqw.eq(BizPosMachineStatusRecords::getDirectlyOrgId ,bizPosMachineStatusRecords.getDirectlyOrgId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getSecondOrgId())){
            lqw.eq(BizPosMachineStatusRecords::getSecondOrgId ,bizPosMachineStatusRecords.getSecondOrgId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getOrderId())){
            lqw.eq(BizPosMachineStatusRecords::getOrderId ,bizPosMachineStatusRecords.getOrderId());
        }
        if (StringUtils.isNotBlank(bizPosMachineStatusRecords.getStatus())){
            lqw.eq(BizPosMachineStatusRecords::getStatus ,bizPosMachineStatusRecords.getStatus());
        }
        return this.list(lqw);
    }
}
