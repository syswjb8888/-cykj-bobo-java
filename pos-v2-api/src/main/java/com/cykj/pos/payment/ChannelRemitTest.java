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
 *对应文档 5.1 银行卡打款申请 （渠道版商户适用，接口文档回标注标准版还是渠道版）
 *
 **/

public class ChannelRemitTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";


    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.remit.api.channelPay", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        RemitBatchRequestDTO remitBatchRequestDTO = new RemitBatchRequestDTO();

        remitBatchRequestDTO.setCustBatchNo(UUID.randomUUID().toString().replace("-",""));
        remitBatchRequestDTO.setBatchNum(2);
        //new BigDecimal 时请写string类型 防止精度丢失
        remitBatchRequestDTO.setBatchAmt(new BigDecimal("100"));
        remitBatchRequestDTO.setServerCallbackUrl("");

        List<RemitDetailRequestDTO> list = new ArrayList<RemitDetailRequestDTO>();
        RemitDetailRequestDTO detail_1 = new RemitDetailRequestDTO();
        detail_1.setCustNo(appId);
        String cust_1 = UUID.randomUUID().toString().replace("-","");
        System.out.println("cust_1  :  "+cust_1);
        detail_1.setCustOrderNo(cust_1);
        //new BigDecimal 时请写string类型 防止精度丢失
        detail_1.setOrderAmt(new BigDecimal("40"));
        detail_1.setRecvCustName("");
        detail_1.setRecvMobile("");
        detail_1.setRecvIdType("");
        detail_1.setRecvIdNo("");
        detail_1.setRecvCardNo("");
        detail_1.setRecvBankName("");
        list.add(detail_1);

        RemitDetailRequestDTO detail_2 = new RemitDetailRequestDTO();
        detail_2.setCustNo(appId);
        String cust_2 = UUID.randomUUID().toString().replace("-","");
        System.out.println("cust_2  :  "+cust_2);
        detail_2.setCustOrderNo(cust_2);
        //new BigDecimal 时请写string类型 防止精度丢失
        detail_2.setOrderAmt(new BigDecimal("60"));
        detail_2.setRecvCustName("");
        detail_2.setRecvMobile("");
        detail_2.setRecvIdType("");
        detail_2.setRecvIdNo("");
        detail_2.setRecvCardNo("");
        detail_2.setRecvBankName("");
        list.add(detail_2);

        remitBatchRequestDTO.setRemitDetailList(list);

        BaseResponse responseDTO = secClient.excute(remitBatchRequestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());

    }

    /*public static void main(String[] args)  throws Exception{
        Map<String ,String> params = new HashMap();
        params.put("app_id","YX0000000000001"); //可以放入请求头中
        params.put("method","settle.remit.api.payment");
        params.put("sign_type","RSA2");
        params.put("timestamp","2013-01-01 08:08:08"); //毫秒
        params.put("version","1.0");
        params.put("merchant_request_no","10000000000");
        TestDTO testDTO = new TestDTO();
        testDTO.setMany("10086");
        testDTO.setOrderNo("这是个订单号");

        List<EntyDTO> list = new ArrayList<>();
        EntyDTO entyDTO = new EntyDTO();
        entyDTO.setTest1("1");
        entyDTO.setTest2("2");
        list.add(entyDTO);
        testDTO.setList(list);
        params.put("biz_content", JsonUtils.ObjectTojson(testDTO));//JSON 可加密
        //参数进行RES签名
        String signa = SecSignature.rsaSign(params, merPrivate,"UTF-8","RSA2");

        System.out.println(signa);

        params.put("sign",signa);

        String resp = WebUtils.doPost("http://127.0.0.1:8003/secopenplatfrom/sec/gateway",params,"UTF-8",100000,100000,null,0);

        System.out.println(resp);
    }*/
}
