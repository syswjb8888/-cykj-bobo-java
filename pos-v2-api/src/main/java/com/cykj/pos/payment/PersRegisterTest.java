/**
 * Created by jiashuai.bao on 2019-06-17.
 */

package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.PersRegisterRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import com.sec.sdk.utils.ImgBase64Util;
import org.junit.Test;

/**
 * 对应文档 4.1 自由职业者注册
 **/

public class PersRegisterTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.register.api.register", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        PersRegisterRequestDTO requestDTO = new PersRegisterRequestDTO();
        requestDTO.setName("");
        requestDTO.setMobile("");
        requestDTO.setIdType("");
        requestDTO.setIdNo("");
        requestDTO.setGender("");
        requestDTO.setBirthday("");
        requestDTO.setIdCardFront(ImgBase64Util.getImageStr("/Users/baojiashuai/Desktop/3.jpg"));
        requestDTO.setIdCardBack(ImgBase64Util.getImageStr("/Users/baojiashuai/Desktop/4.jpg"));

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());
    }
}
