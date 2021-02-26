/**
 * Created by jiashuai.bao on 2019-06-17.
 */


package com.cykj.pos.payment;
import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.PersSignQueryRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import org.junit.Test;

/**
 *
 * 对应文档 4.3 签约查询
 *
 **/

public class PersSignQueryTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.register.api.signquery", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        PersSignQueryRequestDTO requestDTO = new PersSignQueryRequestDTO();
        requestDTO.setContractNo("");
        requestDTO.setPersNo("");

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());
    }
}
