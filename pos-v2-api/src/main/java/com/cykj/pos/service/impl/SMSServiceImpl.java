package com.cykj.pos.service.impl;

import com.alibaba.fastjson.JSONException;
import com.cykj.pos.service.ISMSService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SMSServiceImpl implements ISMSService {

    private static final String SECRET_KEY = "773b6fb4c52a0b3dee64d20a6dbb9897";
    private static final Integer SECRET_ID = 1400447243;
    private static final Integer VERIFYCODE_TEMPLATE_ID =770977;
    private static final Integer PASSWORD_TEMPLATE_ID =770977;
    // private static final String SIGN_NAME ="众盛科技有限公司";
    private static final String SIGN_NAME ="众鑫助手";

    @Override
    public SmsSingleSenderResult sendVerifyCode(String mobile, String verifyCode, String purpose) {
        String[] phoneNumbers = {mobile};
        try {
            String[] params = {verifyCode};
            SmsSingleSender ssender = new SmsSingleSender(SECRET_ID, SECRET_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    VERIFYCODE_TEMPLATE_ID, params, SIGN_NAME, "", purpose);
            System.out.println(result);
            return result;
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public SmsSingleSenderResult sendPassword(String mobile, String verifyCode, String purpose) {
        String[] phoneNumbers = {mobile};
        try {
            String[] params = {verifyCode};
            SmsSingleSender ssender = new SmsSingleSender(SECRET_ID, SECRET_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    PASSWORD_TEMPLATE_ID, params, SIGN_NAME, "", purpose);
            System.out.println(result);
            return result;
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        return null;
    }
}
