package com.cykj.pos.payment.controller;
/**
 *
 */
import com.sec.sdk.constants.SecGatewayConstants;
import com.sec.sdk.exception.SecGatewayException;
import com.sec.sdk.utils.JsonUtils;
import com.sec.sdk.utils.SecSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *  回调测试类 是个Controller 可以自行放入系统中使用，因为sdk不包含spring相关包，故注释掉了
 **/

@RestController
public class CallbackController {
    private Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @RequestMapping(value = "/callBack", method = RequestMethod.POST)
    public String handlePostRequest(HttpServletRequest request){

        logger.info("进入回调服务.");

        //云享平台公钥
        String secPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIh4dbuxTOA63kTTQDNa6wOvbqAe28N/zd4e1c913Fc1F16vKQ/vaxpeocYf7yBnuEyQZoEkBOoQQcLeTLXvyTSyvz4wLcqwmlvIhH37pFs+MLWb/ovZ3nbd1L15RNpGNR59pnUSwSKkjwsbgtFng/ptMWZwUkv11egs+2UjpsltHtaEkw/w2Ai0N8JZTqaFdCnqZGkht4CSVQKOMZrAeF4AD1Siwfk0PqsFGQeJh1/iMdEA7+6P/s2K+OZjT0833nsM5MSoPOHJO4Y3mA+95KFLt30icJGNXm2U/jN4ykD6HPG4X0G8J2g8SuhB86YIOeSQB53afArB2ayfievz5QIDAQAB";

        Map<String, String> params = new HashMap<>();
        params.put(SecGatewayConstants.METHOD,request.getParameter(SecGatewayConstants.METHOD));
        params.put(SecGatewayConstants.SIGN_TYPE,request.getParameter(SecGatewayConstants.SIGN_TYPE));
        params.put(SecGatewayConstants.SIGN,request.getParameter(SecGatewayConstants.SIGN));
        params.put(SecGatewayConstants.TIMESTAMP,request.getParameter(SecGatewayConstants.TIMESTAMP));
        params.put(SecGatewayConstants.VERSION,request.getParameter(SecGatewayConstants.VERSION));
        params.put(SecGatewayConstants.MERCHANT_REQUEST_NO,request.getParameter(SecGatewayConstants.MERCHANT_REQUEST_NO));
        params.put(SecGatewayConstants.BIZ_CONTENT_KEY,request.getParameter(SecGatewayConstants.BIZ_CONTENT_KEY));

        logger.info("参数：{}", JsonUtils.ObjectTojson(params));

        try {
            boolean flag = SecSignature.rsaCheckV2(params, secPublicKey,SecGatewayConstants.CHARSET_UTF8,SecGatewayConstants.SIGN_TYPE_RSA2);
            logger.info("验签结果：{}",flag);
            logger.info("业务Json:{}",request.getParameter(SecGatewayConstants.BIZ_CONTENT_KEY));
        } catch (SecGatewayException e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }
}
