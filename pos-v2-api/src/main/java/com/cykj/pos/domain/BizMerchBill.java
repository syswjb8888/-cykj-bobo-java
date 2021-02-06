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
 * 账单信息对象 biz_merch_bill
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_merch_bill")
public class BizMerchBill implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "bill_id")
    private Long billId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long merchId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String merchName;

    /** 设备SN码 */
    @Excel(name = "设备SN码")
    private String posCode;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String posType;

    /** 款项类型 */
    @Excel(name = "款项类型")
    private String billType;

    /** 金额 */
    @Excel(name = "金额")
    private Long amount;

    /** 政策ID */
    @Excel(name = "政策ID")
    private String policyId;

    /** 账单日期 */
    @Excel(name = "账单日期")
    private String billDate;

    /** $column.columnComment */
    @Excel(name = "账单日期")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "账单日期")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "账单日期")
    private String var3;

    /** $column.columnComment */
    @Excel(name = "账单日期")
    private String var4;

    /** $column.columnComment */
    @Excel(name = "账单日期")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
