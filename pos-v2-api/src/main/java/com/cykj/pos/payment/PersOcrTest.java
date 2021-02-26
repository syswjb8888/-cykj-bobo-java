/**
 * Created by jiashuai.bao on 2019-06-17.
 */

package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.PersOcrRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import com.sec.sdk.utils.ImgBase64Util;
import org.junit.Test;

/**
 *
 * 对应文档 4.4 身份证识别
 *
 **/

public class PersOcrTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.register.api.ocr", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        PersOcrRequestDTO requestDTO = new PersOcrRequestDTO();
        requestDTO.setPersNo("");
        requestDTO.setIdCardFront(ImgBase64Util.getImageStr("/Users/baojiashuai/Desktop/1.jpg"));
        requestDTO.setIdCardBack(ImgBase64Util.getImageStr("/Users/baojiashuai/Desktop/1.jpg"));

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());
    }
}
