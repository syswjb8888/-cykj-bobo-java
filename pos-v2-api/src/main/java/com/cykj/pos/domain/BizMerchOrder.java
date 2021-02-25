package com.cykj.pos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.cykj.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 订单对象 biz_merch_order
 *
 * @author weijianbo
 * @date 2021-02-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_merch_order")
public class BizMerchOrder implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "order_id")
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 商户id */
    @Excel(name = "商户id")
    private Long merchId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 数量 */
    @Excel(name = "数量")
    private Long num;

    /** 消费金额（积分 或者 现金） */
    @Excel(name = "消费金额" , readConverterExp = "积=分,或=者,现=金")
    private Long money;

    /** 商户的父主键 */
    @Excel(name = "商户的父主键")
    private Long parentId;

    /** 父伙伴名称 */
    @Excel(name = "父伙伴名称")
    private String parentName;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Integer status;

    /** 积分类型 （1-积分兑换 2-现金支付) */
    @Excel(name = "积分类型 " , readConverterExp = "积分类型 （1-积分兑换 2-现金支付)")
    private Integer type;

    /** 物流id (积分兑换不用物流) */
    @Excel(name = "物流id (积分兑换不用物流)")
    private Long logisticsId;

    /** 创建时间 */
    private String createTime;

    /** 配送方式 */
    private String dispatchType;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
