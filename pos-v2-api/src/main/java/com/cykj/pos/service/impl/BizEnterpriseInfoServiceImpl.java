package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizEnterpriseInfoMapper;
import com.cykj.pos.domain.BizEnterpriseInfo;
import com.cykj.pos.service.IBizEnterpriseInfoService;

import java.util.List;
import java.util.Map;

/**
 * 企业商户报件信息Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Service
public class BizEnterpriseInfoServiceImpl extends ServiceImpl<BizEnterpriseInfoMapper, BizEnterpriseInfo> implements IBizEnterpriseInfoService {

    @Override
    public List<BizEnterpriseInfo> queryList(BizEnterpriseInfo bizEnterpriseInfo) {
        LambdaQueryWrapper<BizEnterpriseInfo> lqw = Wrappers.lambdaQuery();
        if (bizEnterpriseInfo.getMerchId() != null){
            lqw.eq(BizEnterpriseInfo::getMerchId ,bizEnterpriseInfo.getMerchId());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonPositive())){
            lqw.eq(BizEnterpriseInfo::getLegalPersonPositive ,bizEnterpriseInfo.getLegalPersonPositive());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonBack())){
            lqw.eq(BizEnterpriseInfo::getLegalPersonBack ,bizEnterpriseInfo.getLegalPersonBack());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonName())){
            lqw.like(BizEnterpriseInfo::getLegalPersonName ,bizEnterpriseInfo.getLegalPersonName());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonIdcard())){
            lqw.eq(BizEnterpriseInfo::getLegalPersonIdcard ,bizEnterpriseInfo.getLegalPersonIdcard());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonDuedate())){
            lqw.eq(BizEnterpriseInfo::getLegalPersonDuedate ,bizEnterpriseInfo.getLegalPersonDuedate());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountType())){
            lqw.eq(BizEnterpriseInfo::getEntAccountType ,bizEnterpriseInfo.getEntAccountType());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getLegalPersonSettle())){
            lqw.eq(BizEnterpriseInfo::getLegalPersonSettle ,bizEnterpriseInfo.getLegalPersonSettle());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountRegion())){
            lqw.eq(BizEnterpriseInfo::getEntAccountRegion ,bizEnterpriseInfo.getEntAccountRegion());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccount())){
            lqw.eq(BizEnterpriseInfo::getEntAccount ,bizEnterpriseInfo.getEntAccount());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountBank())){
            lqw.eq(BizEnterpriseInfo::getEntAccountBank ,bizEnterpriseInfo.getEntAccountBank());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountName())){
            lqw.like(BizEnterpriseInfo::getEntAccountName ,bizEnterpriseInfo.getEntAccountName());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountMobile())){
            lqw.eq(BizEnterpriseInfo::getEntAccountMobile ,bizEnterpriseInfo.getEntAccountMobile());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntAccountCardpic())){
            lqw.eq(BizEnterpriseInfo::getEntAccountCardpic ,bizEnterpriseInfo.getEntAccountCardpic());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntLicenseType())){
            lqw.eq(BizEnterpriseInfo::getEntLicenseType ,bizEnterpriseInfo.getEntLicenseType());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntLicenseCode())){
            lqw.eq(BizEnterpriseInfo::getEntLicenseCode ,bizEnterpriseInfo.getEntLicenseCode());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntLicenseDuedate())){
            lqw.eq(BizEnterpriseInfo::getEntLicenseDuedate ,bizEnterpriseInfo.getEntLicenseDuedate());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntLicensePositive())){
            lqw.eq(BizEnterpriseInfo::getEntLicensePositive ,bizEnterpriseInfo.getEntLicensePositive());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntLicenseBack())){
            lqw.eq(BizEnterpriseInfo::getEntLicenseBack ,bizEnterpriseInfo.getEntLicenseBack());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntPositionPic1())){
            lqw.eq(BizEnterpriseInfo::getEntPositionPic1 ,bizEnterpriseInfo.getEntPositionPic1());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntPositionPic2())){
            lqw.eq(BizEnterpriseInfo::getEntPositionPic2 ,bizEnterpriseInfo.getEntPositionPic2());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntPositionPic3())){
            lqw.eq(BizEnterpriseInfo::getEntPositionPic3 ,bizEnterpriseInfo.getEntPositionPic3());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getEntPositionPic4())){
            lqw.eq(BizEnterpriseInfo::getEntPositionPic4 ,bizEnterpriseInfo.getEntPositionPic4());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getVar1())){
            lqw.eq(BizEnterpriseInfo::getVar1 ,bizEnterpriseInfo.getVar1());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getVar2())){
            lqw.eq(BizEnterpriseInfo::getVar2 ,bizEnterpriseInfo.getVar2());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getVar3())){
            lqw.eq(BizEnterpriseInfo::getVar3 ,bizEnterpriseInfo.getVar3());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getVar4())){
            lqw.eq(BizEnterpriseInfo::getVar4 ,bizEnterpriseInfo.getVar4());
        }
        if (StringUtils.isNotBlank(bizEnterpriseInfo.getVar5())){
            lqw.eq(BizEnterpriseInfo::getVar5 ,bizEnterpriseInfo.getVar5());
        }
        return this.list(lqw);
    }
}
