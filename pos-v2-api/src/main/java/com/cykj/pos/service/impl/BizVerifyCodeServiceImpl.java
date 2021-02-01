package com.cykj.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.constant.Constants;
import com.cykj.pos.domain.BizVerifyCode;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.mapper.BizVerifyCodeMapper;
import com.cykj.pos.service.IBizVerifyCodeService;
import com.cykj.pos.service.ISMSService;
import com.cykj.pos.util.DateUtils;
import com.cykj.pos.util.VerifyCodeUtils;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 短信验证码Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
@Service
public class BizVerifyCodeServiceImpl extends ServiceImpl<BizVerifyCodeMapper, BizVerifyCode> implements IBizVerifyCodeService {

    @Autowired
    private ISMSService smsService;

    @Autowired
    private RuoYiConfig config;

    @Override
    public List<BizVerifyCode> queryList(BizVerifyCode bizVerifyCode) {
        LambdaQueryWrapper<BizVerifyCode> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizVerifyCode.getVerCode())){
            lqw.eq(BizVerifyCode::getVerCode ,bizVerifyCode.getVerCode());
        }
        if (bizVerifyCode.getVerStarttime() != null){
            lqw.eq(BizVerifyCode::getVerStarttime ,bizVerifyCode.getVerStarttime());
        }
        if (bizVerifyCode.getVerEndtime() != null){
            lqw.eq(BizVerifyCode::getVerEndtime ,bizVerifyCode.getVerEndtime());
        }
        if (bizVerifyCode.getVerStatus() != null){
            lqw.eq(BizVerifyCode::getVerStatus ,bizVerifyCode.getVerStatus());
        }
        if (StringUtils.isNotBlank(bizVerifyCode.getVerMobile())){
            lqw.eq(BizVerifyCode::getVerMobile ,bizVerifyCode.getVerMobile());
        }
        if (bizVerifyCode.getVerUserId() != null){
            lqw.eq(BizVerifyCode::getVerUserId ,bizVerifyCode.getVerUserId());
        }
        if (StringUtils.isNotBlank(bizVerifyCode.getVar1())){
            lqw.eq(BizVerifyCode::getVar1 ,bizVerifyCode.getVar1());
        }
        if (StringUtils.isNotBlank(bizVerifyCode.getVar2())){
            lqw.eq(BizVerifyCode::getVar2 ,bizVerifyCode.getVar2());
        }
        if (StringUtils.isNotBlank(bizVerifyCode.getVar3())){
            lqw.eq(BizVerifyCode::getVar3 ,bizVerifyCode.getVar3());
        }
        return this.list(lqw);
    }

    @Override
    public BizStatusContantEnum sendVerifyCode(String mobile,Long userId){
        String code = VerifyCodeUtils.getVerifyCode();
        SmsSingleSenderResult result = smsService.sendVerifyCode(mobile,code,"partner register");
        if(result != null && result.result == 0){
            BizVerifyCode verifyCode = new BizVerifyCode();
            verifyCode.setVerCode(code);
            verifyCode.setVerMobile(mobile);

            LocalDateTime localDateTime = LocalDateTime.now();
            String startTime = DateUtils.localeDateTime2String(localDateTime, Constants.DATETIME_FORMATTER);
            verifyCode.setVerStarttime(startTime);

            Integer smsEffectiveDuration = config.getSmsEffectiveDuration();
            LocalDateTime endLocalTime = localDateTime.plusMinutes(smsEffectiveDuration);
            String endTime = DateUtils.localeDateTime2String(endLocalTime, Constants.DATETIME_FORMATTER);
            verifyCode.setVerEndtime(endTime);

            verifyCode.setVerUserId(userId);
            verifyCode.setVar1(result.sid);
            this.save(verifyCode);
            return BizStatusContantEnum.BIZ_SUCCESS;
        }else if(result.result == 1023){
            return BizStatusContantEnum.SMS_FAIL_FREQUENCY;
        }
        return BizStatusContantEnum.SMS_FAIL;
    }

    @Override
    public BizStatusContantEnum verifyCodeValidate(String mobile, String verifyCode){
        LambdaQueryWrapper<BizVerifyCode> lqw = Wrappers.lambdaQuery();
        lqw.eq(BizVerifyCode::getVerCode ,verifyCode);
        lqw.eq(BizVerifyCode::getVerStatus ,0);
        lqw.eq(BizVerifyCode::getVerMobile ,mobile);
        BizVerifyCode bizVerifyCode = this.getOne(lqw);
        if(bizVerifyCode == null){
            return BizStatusContantEnum.SMS_INVALIDATED;
        }
        String endTime = bizVerifyCode.getVerEndtime();
        LocalDateTime endLocalTime = DateUtils.stringToLocalDateTime(endTime, Constants.DATETIME_FORMATTER);
        if(endLocalTime.compareTo(LocalDateTime.now()) < 0){
            bizVerifyCode.setVerStatus(1);
            this.saveOrUpdate(bizVerifyCode);
            return BizStatusContantEnum.SMS_DURATION_EXPIRED;
        }
        bizVerifyCode.setVerStatus(1);
        this.saveOrUpdate(bizVerifyCode);
        return BizStatusContantEnum.SMS_SUCCESS;
    }
}
