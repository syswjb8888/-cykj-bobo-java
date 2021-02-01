package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartnerInviteDTO {

    @ApiModelProperty(value = "邀请码，即内部商户编码")
    private String invitorUserId;

    @ApiModelProperty(value = "手机号，即商户登录账号")
    private String mobile;

    @ApiModelProperty(value = "密码，即商户登录密码")
    private String password;

    @ApiModelProperty(value = "短信验证码")
    private String verifyCode;
}
