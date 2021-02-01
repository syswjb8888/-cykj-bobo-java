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
 * 交易记录对象 biz_trans_records
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_trans_records")
public class BizTransRecords implements Serializable {

private static final long serialVersionUID=1L;


    /** 交易记录主键 */
    @TableId(value = "trans_id")
    private Long transId;

    /** 代理商账号 */
    @Excel(name = "代理商账号")
    private String agentNo;

    /** 平台唯一流水号 */
    @Excel(name = "平台唯一流水号")
    private String keyRsp;

    /** 交易卡号 */
    @Excel(name = "交易卡号")
    private String cardNo;

    /** 发卡行名称 */
    @Excel(name = "发卡行名称")
    private String cardBankName;

    /** 交易类型 */
    @Excel(name = "交易类型")
    private String transType;

    /** 交易名称 */
    @Excel(name = "交易名称")
    private String transName;

    /** 交易金额 */
    @Excel(name = "交易金额")
    private BigDecimal transAmount;

    /** 商户号 */
    @Excel(name = "商户号")
    private String merchCode;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String merchName;

    /** SN号 */
    @Excel(name = "SN号")
    private String posCode;

    /** 系统参考号 */
    @Excel(name = "系统参考号")
    private String retriRefNo;

    /** 交易卡类型 */
    @Excel(name = "交易卡类型")
    private String cardClass;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal transFree;

    /** 交易日期 */
    @Excel(name = "交易日期")
    private String transDate;

    /** 交易时间 */
    @Excel(name = "交易时间")
    private String transTime;

    /** 行业类别 */
    @Excel(name = "行业类别")
    private String category;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String posEntry;

    /** 冻结标识 */
    @Excel(name = "冻结标识")
    private String freezeFlag;

    /** 精选标识 */
    @Excel(name = "精选标识")
    private String choiceFlag;

    /** 服务费 */
    @Excel(name = "服务费")
    private BigDecimal stlmFree;

    /** 通讯费 */
    @Excel(name = "通讯费")
    private BigDecimal commFree;

    /** 通信规则 */
    @Excel(name = "通信规则")
    private String commFreeRule;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Long updateBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var3;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var4;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var5;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var6;

    /** $column.columnComment */
    @Excel(name = "通信规则")
    private String var7;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
