package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMicroInfoMapper;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.service.IBizMicroInfoService;

import java.util.List;
import java.util.Map;

/**
 * 报件小微商户信息Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Service
public class BizMicroInfoServiceImpl extends ServiceImpl<BizMicroInfoMapper, BizMicroInfo> implements IBizMicroInfoService {

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMicroInfo> queryList(BizMicroInfo bizMicroInfo) {
        LambdaQueryWrapper<BizMicroInfo> lqw = Wrappers.lambdaQuery();
        if (bizMicroInfo.getMerchId() != null){
            lqw.eq(BizMicroInfo::getMerchId ,bizMicroInfo.getMerchId());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchBank())){
            lqw.eq(BizMicroInfo::getMerchBank ,bizMicroInfo.getMerchBank());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchBankCardno())){
            lqw.eq(BizMicroInfo::getMerchBankCardno ,bizMicroInfo.getMerchBankCardno());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchBankCity())){
            lqw.eq(BizMicroInfo::getMerchBankCity ,bizMicroInfo.getMerchBankCity());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchBankMobile())){
            lqw.eq(BizMicroInfo::getMerchBankMobile ,bizMicroInfo.getMerchBankMobile());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchBankCard())){
            lqw.eq(BizMicroInfo::getMerchBankCard ,bizMicroInfo.getMerchBankCard());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcardPositive())){
            lqw.eq(BizMicroInfo::getMerchIdcardPositive ,bizMicroInfo.getMerchIdcardPositive());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcardBack())){
            lqw.eq(BizMicroInfo::getMerchIdcardBack ,bizMicroInfo.getMerchIdcardBack());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcardHand())){
            lqw.eq(BizMicroInfo::getMerchIdcardHand ,bizMicroInfo.getMerchIdcardHand());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcard())){
            lqw.eq(BizMicroInfo::getMerchIdcard ,bizMicroInfo.getMerchIdcard());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcardName())){
            lqw.like(BizMicroInfo::getMerchIdcardName ,bizMicroInfo.getMerchIdcardName());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getMerchIdcardDuedate())){
            lqw.eq(BizMicroInfo::getMerchIdcardDuedate ,bizMicroInfo.getMerchIdcardDuedate());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getVar1())){
            lqw.eq(BizMicroInfo::getVar1 ,bizMicroInfo.getVar1());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getVar2())){
            lqw.eq(BizMicroInfo::getVar2 ,bizMicroInfo.getVar2());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getVar3())){
            lqw.eq(BizMicroInfo::getVar3 ,bizMicroInfo.getVar3());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getVar4())){
            lqw.eq(BizMicroInfo::getVar4 ,bizMicroInfo.getVar4());
        }
        if (StringUtils.isNotBlank(bizMicroInfo.getVar5())){
            lqw.eq(BizMicroInfo::getVar5 ,bizMicroInfo.getVar5());
        }
        return this.list(lqw);
    }
    @DataSource(DataSourceType.SLAVE)
    @Override
    public BizMicroInfo getBizMicroInfoByMerchId(Long merchId){
        LambdaQueryWrapper<BizMicroInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(BizMicroInfo::getMerchId ,merchId);
        return this.getOne(lqw);
    }
}
