package com.cykj.pos.service;

import com.github.qcloudsms.SmsSingleSenderResult;

public interface ISMSService {

    /**
     * 短信发送验证码
     * @param mobile 接收手机号
     * @param verifyCode 短信验证码
     * @param purpose 用途
     */
    public SmsSingleSenderResult sendVerifyCode(String mobile, String verifyCode, String purpose);

    /**
     * 短信发送登录密码
     * @param mobile
     * @param verifyCode
     * @param purpose
     * @return
     */
    public SmsSingleSenderResult sendPassword(String mobile, String verifyCode, String purpose);
}
