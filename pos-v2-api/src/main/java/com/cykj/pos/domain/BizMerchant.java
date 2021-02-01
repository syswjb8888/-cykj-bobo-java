package com.cykj.pos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
import java.util.*;
import java.math.BigDecimal;

/**
 * 报件商户信息对象 biz_merchart
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_merchant")
public class BizMerchant implements Serializable {

private static final long serialVersionUID=1L;


    /** 商户主键 */
    @TableId(value = "merch_id")
    @ApiModelProperty(value = "商户主键")
    private Long merchId;

    /** 关联登录用户主键 */
    @Excel(name = "商户所属用户主键")
    @ApiModelProperty(value = "商户所属用户主键")
    private Long userId;

    /** 上级商户主键 */
    @Excel(name = "上级商户主键")
    @ApiModelProperty(value = "上级商户主键")
    private Long parentId;

    /** 系统自定义商户编码 */
    @Excel(name = "系统自定义商户编码")
    @ApiModelProperty(value = "商户编码")
    private String merchCode;

    /** 商户全称 */
    @Excel(name = "商户全称")
    @ApiModelProperty(value = "商户全称")
    private String merchName;

    /** 商户简称 */
    @Excel(name = "商户简称")
    @ApiModelProperty(value = "商户简称")
    private String merchAbbr;

    /** 商户所属区域 */
    @Excel(name = "商户所属区域")
    @ApiModelProperty(value = "商户所属区县地区代码")
    private String merchRegion;

    /** 商户详细地址 */
    @Excel(name = "商户详细地址")
    @ApiModelProperty(value = "商户详细地址")
    private String merchAddr;

    /** 经营范围 */
    @Excel(name = "经营范围")
    @ApiModelProperty(value = "经营范围")
    private String merchBizScope;

    /** 商户类型 */
    @Excel(name = "商户类型")
    @ApiModelProperty(value = "商户类型,1-小微商户，2-企业商户")
    private String merchType;

    /** 审核状态 */
    @Excel(name = "审核状态")
    @ApiModelProperty(hidden = true)
    private String verifyStatus;

    /** 审核驳回原因 */
    @Excel(name = "审核驳回原因")
    @ApiModelProperty(hidden = true)
    private String verifyResult;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Long createBy;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Long updateBy;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /** $column.columnComment */
    @ApiModelProperty(value = "省地区代码")
    private String var1;

    /** $column.columnComment */
    @ApiModelProperty(value = "市地区代码")
    private String var2;

    /** $column.columnComment */
    @ApiModelProperty(value = "区县地区代码")
    private String var3;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var4;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var5;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    List<BizMerchant> children = new ArrayList<>();

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Map<String, Object> params = new HashMap<>();
}
