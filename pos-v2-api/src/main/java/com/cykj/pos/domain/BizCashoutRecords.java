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
 * 提现记录对象 biz_cashout_records
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_cashout_records")
public class BizCashoutRecords implements Serializable {

private static final long serialVersionUID=1L;


    /** 提现主键 */
    @TableId(value = "cashout_id")
    private Long cashoutId;

    /** 提现钱包 */
    @Excel(name = "提现钱包")
    private Long walletId;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal cashoutAmount;

    /** 提现银行账户 */
    @Excel(name = "提现银行账户")
    private String dealAccount;

    /** 提现账户所属银行 */
    @Excel(name = "提现账户所属银行")
    private String dealBank;

    /** 支付账户 */
    @Excel(name = "支付账户")
    private String payAccount;

    /** 支付账户所属银行 */
    @Excel(name = "支付账户所属银行")
    private String payAccountBank;

    /** 提现类型 */
    @Excel(name = "提现类型")
    private String cashoutType;

    /** 提现状态 */
    @Excel(name = "提现状态")
    private String cashoutStatus;

    /** 提现人 */
    private Long createBy;

    /** 提现日期时间 */
    private Date createTime;

    /** $column.columnComment */
    @Excel(name = "提现状态")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "提现状态")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "提现状态")
    private String var3;

    /** $column.columnComment */
    @Excel(name = "提现状态")
    private String var4;

    /** $column.columnComment */
    @Excel(name = "提现状态")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
