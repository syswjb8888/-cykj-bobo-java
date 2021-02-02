package com.cykj.pos.profit.dto;

import lombok.Data;

@Data
public class TerminalBindDTO {

    /** 用户商编 */
    private String merchantId;

    /** 商户终端ID */
    private String terminalId;

    /** 设备SN号 */
    private String snCode;

    /** 设备类型 */
    private String deviceType;

    /** 产品名称 */
    private String receiptType;

    /** 二级机构号 */
    private String secondOrgID;

    /** 直属机构号 */
    private String directlyOrgID;

    /** 姓名 */
    private String name;

    /** 手机号 */
    private String phoneNo;

    /** 身份证号 */
    private String idCardNo;

    /** 绑定时间 */
    private String time;

    /** 绑定状态 */
    private String status;

}
