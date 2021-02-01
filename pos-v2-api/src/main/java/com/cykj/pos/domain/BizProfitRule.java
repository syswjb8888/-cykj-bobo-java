package com.cykj.pos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cykj.common.annotation.Excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 分润规则制订对象 biz_profit_rule
 *
 * @author ningbingwu
 * @date 2021-01-08
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_profit_rule")
public class BizProfitRule implements Serializable {

private static final long serialVersionUID=1L;


    /** 分润编号 */
    @TableId(value = "profit_id")
    private Long profitId;

    /** 分润下限 */
    @Excel(name = "分润下限")
    private BigDecimal profitMin;

    /** 分润上限 */
    @Excel(name = "分润上限")
    private BigDecimal profitMax;

    /** 分润单位 */
    @Excel(name = "分润单位")
    private String profitUnit;

    /** 分润类型 */
    @Excel(name = "分润类型")
    private String profitType;

    /** 分润金额 */
    @Excel(name = "分润金额")
    private BigDecimal profitAmount;

    /** 分润封顶 */
    @Excel(name = "分润封顶")
    private BigDecimal profitTop;

    /** 分润档次 */
    @Excel(name = "分润档次")
    private Integer profitLevel;

    @Excel(name = "每月最低交易流水")
    private BigDecimal monthlyMin;

    @Excel(name = "每月千万级交易流水团队数")
    private Integer monthlyTomTeams;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private String createBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    private String updateBy;

    /** $column.columnComment */
    @Excel(name = "备注")
    private String remark;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
