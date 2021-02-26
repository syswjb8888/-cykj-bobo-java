/**
 * Created by jiashuai.bao on 2019-06-17.
 */

package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.CustCheckAccountRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import org.junit.Test;

/**
 * 5.5商户交易流水对账文件下载
 **/

public class CustCheckAccountTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{
        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.cust.api.check", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        CustCheckAccountRequestDTO requestDTO = new CustCheckAccountRequestDTO();
        requestDTO.setCheckDate("");

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());
    }
}
