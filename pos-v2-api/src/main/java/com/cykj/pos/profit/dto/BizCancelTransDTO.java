package com.cykj.pos.profit.dto;

import com.cykj.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BizCancelTransDTO {
    /** 用户商编 */
    private String merchantId;

    /** 外部订单号 */
    private String outTraceNo;

    /** 交易金额（元） */
    private String amt;

    /** 交易编号 */
    private String idTxn;

    /** 原交易外部订单号 */
    private String originOutTraceNo;

    /** 交易请求时间,格式：yyyy-MM-dd HH:mm:ss.SSS*/
    private String txnTime;

    /** 二级机构编号 */
    private String secondOrgID;

    /** 直属机构编号 */
    private String directlyOrgID;
}
