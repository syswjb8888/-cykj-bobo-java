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
 * 政策管理对象 biz_pos_policy
 *
 * @author weijianbo
 * @date 2021-02-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_pos_policy")
public class BizPosPolicy implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 政策编号 */
    @Excel(name = "政策编号")
    private String policyId;

    /** 返现金额 */
    @Excel(name = "返现金额")
    private BigDecimal returnMoney;

    /** 税点 */
    @Excel(name = "税点")
    private BigDecimal taxation;

    /** 积分 */
    @Excel(name = "积分")
    private Long returnIntegral;

    /** 月数 */
    @Excel(name = "月数")
    private Long months;

    /** 累计完成金额 */
    @Excel(name = "累计完成金额")
    private BigDecimal amounts;

    /** 冻结金额 */
    @Excel(name = "冻结金额")
    private BigDecimal frozenAmount;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String var1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
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
