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
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 报件小微商户信息对象 biz_micro_info
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_micro_info")
public class BizMicroInfo implements Serializable {

private static final long serialVersionUID=1L;


    /** 商户主键 */
    @TableId(value = "micro_id")
    private Long microId;

    /** 商户主键 */
    @Excel(name = "商户主键")
    @ApiModelProperty(value = "商户主键")
    private Long merchId;

    /** 结算银行卡开户行 */
    @Excel(name = "结算银行卡开户行")
    @ApiModelProperty(value = "结算银行卡开户行")
    private String merchBank;

    /** 结算银行卡号 */
    @Excel(name = "结算银行卡号")
    @ApiModelProperty(value = "结算银行卡号")
    private String merchBankCardno;

    /** 开户行所在城市 */
    @Excel(name = "开户行所在城市")
    @ApiModelProperty(value = "开户行所在城市")
    private String merchBankCity;

    /** 银行预留手机号 */
    @Excel(name = "银行预留手机号")
    @ApiModelProperty(value = "银行预留手机号")
    private String merchBankMobile;

    /** 银行卡照片 */
    @Excel(name = "银行卡照片")
    @ApiModelProperty(value = "银行卡照片地址")
    private String merchBankCard;

    /** 身份证件正面照 */
    @Excel(name = "身份证件正面照")
    @ApiModelProperty(value = "身份证件正面照地址")
    private String merchIdcardPositive;

    /** 身份证件背面照 */
    @Excel(name = "身份证件背面照")
    @ApiModelProperty(value = "身份证件背面照地址")
    private String merchIdcardBack;

    /** 身份证件手持照片 */
    @Excel(name = "身份证件手持照片")
    @ApiModelProperty(value = "身份证件手持照片地址")
    private String merchIdcardHand;

    /** 身份证件号码 */
    @Excel(name = "身份证件号码")
    @ApiModelProperty(value = "身份证件号码")
    private String merchIdcard;

    /** 姓名 */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String merchIdcardName;

    /** 证件到期日 */
    @Excel(name = "证件有效期")
    @ApiModelProperty(value = "证件有效期")
    private String merchIdcardDuedate;

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
    @ApiModelProperty(hidden = true)
    private String var1;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var2;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var3;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var4;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var5;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Map<String, Object> params = new HashMap<>();
}
