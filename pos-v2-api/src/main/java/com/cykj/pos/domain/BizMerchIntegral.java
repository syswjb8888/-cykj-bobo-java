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
 * 积分管理对象 biz_merch_integral
 *
 * @author weijianbo
 * @date 2021-02-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_merch_integral")
public class BizMerchIntegral implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 商户id */
    @Excel(name = "商户id")
    private Long merchId;

    /** 设备SN码 */
    @Excel(name = "设备SN码")
    private String posCode;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private String integralType;

    /** 积分值 */
    @Excel(name = "积分值")
    private Long value;

    /** 日期 */
    private Date createTime;

    /** 交易类型 */
    @Excel(name = "交易类型")
    private String transType;

    /** 交易订单编号 */
    @Excel(name = "交易订单编号")
    private String orderId;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String var1;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String var2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String var3;

    /** 备用字段4 */
    @Excel(name = "备用字段4")
    private String var4;

    /** 备用字段5 */
    @Excel(name = "备用字段5")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
