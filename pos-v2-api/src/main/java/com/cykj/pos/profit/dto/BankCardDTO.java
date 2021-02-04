package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BankCardDTO {

    @ApiModelProperty(value = "登录用户主键")
    private Long userId;

    @ApiModelProperty(value = "开户行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "开户行所在城市")
    private String bankCity;

    @ApiModelProperty(value = "银行预留手机号")
    private String bankReservedMobile;

    @ApiModelProperty(value = "开户人姓名")
    private String idCardName;

    @ApiModelProperty(value = "开户人身份证件号码")
    private String idCard;

    @ApiModelProperty(value = "银行卡照片URL")
    private String bankCardImg;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

}
