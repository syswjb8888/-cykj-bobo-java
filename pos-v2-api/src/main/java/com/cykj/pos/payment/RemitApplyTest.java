/**
 * Created by jiashuai.bao on 2019-06-17.
 */

package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.RemitBatchRequestDTO;
import com.sec.sdk.bean.RemitDetailRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * 对应文档 5.1 付款申请 （标准版商户适用，接口文档回标注标准版还是渠道版）
 *
 **/

public class RemitApplyTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.remit.api.payment", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        RemitBatchRequestDTO remitBatchRequestDTO = new RemitBatchRequestDTO();

        remitBatchRequestDTO.setCustBatchNo(UUID.randomUUID().toString().replace("-",""));
        remitBatchRequestDTO.setBatchNum(2);
        //new BigDecimal 时请写string类型 防止精度丢失
        remitBatchRequestDTO.setBatchAmt(new BigDecimal("40.06"));
        remitBatchRequestDTO.setServerCallbackUrl("http://39.97.110.167/sop/callBack");

        List<RemitDetailRequestDTO> list = new ArrayList<>();
        RemitDetailRequestDTO detail_1 = new RemitDetailRequestDTO();
        detail_1.setCustNo(appId);
        String cust_1 = UUID.randomUUID().toString().replace("-","");
        System.out.println("cust_1  :  "+cust_1);
        detail_1.setCustOrderNo(cust_1);
        System.out.println("cust_1  :  "+cust_1);
        //new BigDecimal 时请写string类型 防止精度丢失
        detail_1.setOrderAmt(new BigDecimal("20.04"));
        detail_1.setRecvCustName("");
        detail_1.setRecvMobile("");
        detail_1.setRecvIdType("IDENTITY");
        detail_1.setRecvIdNo("");
        detail_1.setRecvCardNo("");
        detail_1.setRecvBankName("");
        list.add(detail_1);

        RemitDetailRequestDTO detail_2 = new RemitDetailRequestDTO();
        detail_2.setCustNo(appId);
        String cust_2 = UUID.randomUUID().toString().replace("-","");
        System.out.println("cust_1  :  "+cust_2);
        detail_2.setCustOrderNo(cust_1);
        //new BigDecimal 时请写string类型 防止精度丢失
        detail_2.setOrderAmt(new BigDecimal("20.02"));
        detail_2.setRecvCustName("");
        detail_2.setRecvMobile("");
        detail_2.setRecvIdType("IDENTITY");
        detail_2.setRecvIdNo("");
        detail_2.setRecvCardNo("");
        detail_2.setRecvBankName("");
        list.add(detail_2);

        remitBatchRequestDTO.setRemitDetailList(list);

        BaseResponse responseDTO = secClient.excute(remitBatchRequestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());

    }
}
