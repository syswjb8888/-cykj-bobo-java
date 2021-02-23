package com.cykj.pos.domain.dto;

import lombok.Data;

@Data
public class IntegralDetailDTO {

    private Long id;

    /** 商户id */
    private Long merchId;

    /** 设备SN码 */
    private String posCode;

    /** 积分类型 */
    private String integralType;

    /** 积分值 */
    private Long value;

    /** 日期 */
    private String createTime;

    /** 交易类型 */
    private String transType;

    /** 交易订单编号 */
    private String orderId;

}
