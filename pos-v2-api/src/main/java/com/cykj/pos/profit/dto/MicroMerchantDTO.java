package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MicroMerchantDTO {

    /** 商户全称 */
    @ApiModelProperty(value = "所属用户主键")
    private Long userId;

    /** 商户全称 */
    @ApiModelProperty(value = "商户全称")
    private String merchName;

    /** 商户简称 */
    @ApiModelProperty(value = "商户简称")
    private String merchAbbr;

    /** 商户所属区域 */
    @ApiModelProperty(value = "商户所属区域-省")
    private String merchProvince;

    /** 商户所属区域 */
    @ApiModelProperty(value = "商户所属区域-市")
    private String merchCity;

    /** 商户所属区域 */
    @ApiModelProperty(value = "商户所属区域-区县")
    private String merchCounty;

    /** 商户详细地址 */
    @ApiModelProperty(value = "商户详细地址")
    private String merchAddr;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围")
    private String merchBizScope;

    /** 商户类型 */
    @ApiModelProperty(value = "商户类型")
    private String merchType;

    /** 结算银行卡开户行 */
    @ApiModelProperty(value = "结算银行卡开户行")
    private String merchBank;

    /** 结算银行卡号 */
    @ApiModelProperty(value = "结算银行卡号")
    private String merchBankCardno;

    /** 开户行所在城市 */
    @ApiModelProperty(value = "开户行所在省")
    private String merchBankProvince;

    /** 开户行所在城市 */
    @ApiModelProperty(value = "开户行所在城市")
    private String merchBankCity;

    /** 银行预留手机号 */
    @ApiModelProperty(value = "银行预留手机号")
    private String merchBankMobile;

    /** 银行卡照片 */
    @ApiModelProperty(value = "银行卡照片")
    private String merchBankCard;

    /** 身份证件正面照 */
    @ApiModelProperty(value = "身份证件正面照")
    private String merchIdcardPositive;

    /** 身份证件背面照 */
    @ApiModelProperty(value = "身份证件背面照")
    private String merchIdcardBack;

    /** 身份证件手持照片 */
    @ApiModelProperty(value = "身份证件手持照片")
    private String merchIdcardHand;

    /** 身份证件号码 */
    @ApiModelProperty(value = "身份证件号码")
    private String merchIdcard;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String merchIdcardName;

    /** 证件到期日 */
    @ApiModelProperty(value = "证件到期日")
    private String merchIdcardDuedate;

    /** 短信验证码 */
    @ApiModelProperty(value = "短信验证码")
    private String verifyCode;
}
