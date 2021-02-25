package com.cykj.pos.domain.dto;

import lombok.Data;

@Data
public class OrderDTO {

    /*订单id*/
    private Long orderId;
    /*用户id*/
    private Long userId;
    /*订单类型   1 我的兑换  2 兑换申请*/
    private Long orderType;
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
    /**页号*/
    private Integer pageNo;
    /**页大小*/
    private Integer pageSize;
    /**数据开始数*/
    private Integer start;
}
