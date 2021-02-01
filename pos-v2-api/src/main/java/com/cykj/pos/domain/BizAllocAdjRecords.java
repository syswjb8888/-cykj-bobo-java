package com.cykj.pos.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cykj.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 划拔回调记录对象 biz_alloc_adj_records
 *
 * @author ningbingwu
 * @date 2021-01-15
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_alloc_adj_records")
public class BizAllocAdjRecords implements Serializable {

private static final long serialVersionUID=1L;


    /** 操作主键 */
    @TableId(value = "oper_id")
    private Long operId;

    /** 操作POS终端 */
    @Excel(name = "操作POS终端")
    private Long posId;

    /** 终端SN系列号 */
    @Excel(name = "终端SN系列号")
    private String posCode;

    /** 原归属商户 */
    @Excel(name = "原归属商户")
    private Long oldUserId;

    /** 现归属商户 */
    @Excel(name = "现归属商户")
    private Long newUserId;

    /** 操作人 */
    @Excel(name = "操作人")
    private Long operator;

    /** 划拔时间 */
    @Excel(name = "操作时间")
    private String operateTime;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private Integer operateType;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    private Long updateBy;

    /** $column.columnComment */
    @Excel(name = "操作类型")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "操作类型")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "操作类型")
    private String var3;

    /** $column.columnComment */
    @Excel(name = "操作类型")
    private String var4;

    /** $column.columnComment */
    @Excel(name = "操作类型")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
