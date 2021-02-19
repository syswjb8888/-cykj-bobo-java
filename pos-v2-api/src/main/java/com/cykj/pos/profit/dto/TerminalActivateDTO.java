package com.cykj.pos.profit.dto;

import lombok.Data;

@Data
public class TerminalActivateDTO {
    /** 用户商编 */
    private String merchantId;

    /** 商户终端ID */
    private String terminalId;

    /** 设备SN号 */
    private String snCode;

    /** 设备类型 */
    private String deviceType;

    /** 激活时间 */
    private String activeTime;

    /** 订单号 */
    private String orderId;

    /** 激活状态 */
    private String isActivated;

    /** 产品名称 */
    private String receiptType;

    /** 二级机构号 */
    private String secondOrgID;

    /** 直属机构号 */
    private String directlyOrgID;

    /** 姓名 */
    private String name;

    /** 交易编号 */
    private String idTxn;

    /** 电话号 */
    private String phoneNo;

    /** 政策编号 */
    private String policyId;


}
