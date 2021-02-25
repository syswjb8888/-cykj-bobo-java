package com.cykj.pos.domain.dto;

import lombok.Data;

@Data
public class OrderDTO {

    /*用户id*/
    private Long userId;
    /*商户id*/
    private Long merchId;
    /*台数*/
    private Long num;
    /*积分金额*/
    private long money;
    /*划拨商户id*/
    private Long parentId;
    /*配送方式*/
    private String dispatchType; // 配送方式
    /*订单编号*/
    private String orderNo;
    /*划拨伙伴姓名*/
    private String parentName;
    /** 创建时间 */
    private String createTime;
}
