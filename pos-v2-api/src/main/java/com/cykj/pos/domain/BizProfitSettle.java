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
 * 分润结算信息对象 biz_profit_settle
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_profit_settle")
public class BizProfitSettle implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "sett_id")
    private Long settId;

    /** $column.columnComment */
    private Long merchId;

    /** 商户编号 */
    @Excel(name = "商户编号")
    private String merchCode;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String merchName;

    /** 月交易额 */
    @Excel(name = "月交易额")
    private BigDecimal monthAmount;

    /** 交易分润 */
    @Excel(name = "交易分润")
    private BigDecimal transProfit;

    /** 管理津贴 */
    @Excel(name = "管理津贴")
    private BigDecimal manageAllowance;

    /** 钻石奖金 */
    @Excel(name = "钻石奖金")
    private BigDecimal diamondReward;

    /** 皇冠奖 */
    @Excel(name = "皇冠奖")
    private BigDecimal crownReward;

    /** 分红奖金 */
    @Excel(name = "分红奖金")
    private BigDecimal bonusReward;

    /** 奖金分红总额 */
    @Excel(name = "奖金分红总额")
    private BigDecimal rewardAmount;

    /** 结算年份 */
    @Excel(name = "结算年份")
    private String caculateYear;

    /** 结算月份 */
    @Excel(name = "结算月份",dictType = "settle_month")
    private String caculateMonth;

    /** 结算日 */
    @Excel(name = "结算日")
    private String caculateDay;

    /** 分润计算日期时间 */
    @Excel(name = "分润计算日期时间")
    private String caculateDate;

    /** 支付日期时间 */
    @Excel(name = "支付日期时间")
    private String settleDate;

    /** 结算支付状态 */
    @Excel(name = "结算支付状态")
    private String settleStatus;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Long updateBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    private String var1;

    /** $column.columnComment */
    private String var2;

    /** $column.columnComment */
    @Excel(name = "结算支付状态")
    private String var3;

    /** $column.columnComment */
    private String var4;

    /** $column.columnComment */
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
