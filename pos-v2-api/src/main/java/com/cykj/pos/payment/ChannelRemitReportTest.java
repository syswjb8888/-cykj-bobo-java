/**
 * Created by jiashuai.bao on 2019-06-17.
 */
package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.ChannelRemitReportRequestDTO;
import com.sec.sdk.bean.RemitReportDetailRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *  对应文档 5.2 银行卡打款信息上报 （渠道版商户适用，接口文档回标注标准版还是渠道版）
 *
 **/

public class ChannelRemitReportTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "";

        SecClient secClient = new SecClient(SecGatewayConstants.SERVER_URL,"settle.remit.api.channelReport", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        ChannelRemitReportRequestDTO requestDTO = new ChannelRemitReportRequestDTO();
        requestDTO.setBatchStatus("");
        requestDTO.setCustBatchNo("");

        List<RemitReportDetailRequestDTO> list = new ArrayList<RemitReportDetailRequestDTO>();

        RemitReportDetailRequestDTO dto_1 = new RemitReportDetailRequestDTO();
        dto_1.setCustOrderNo("");
        dto_1.setOrderStatus("");
        dto_1.setCustNo(appId);
        list.add(dto_1);

        RemitReportDetailRequestDTO dto_2 = new RemitReportDetailRequestDTO();
        dto_2.setCustOrderNo("");
        dto_2.setOrderStatus("");
        dto_2.setCustNo(appId);
        list.add(dto_2);

        requestDTO.setRemitDetailList(list);

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());

    }
}
