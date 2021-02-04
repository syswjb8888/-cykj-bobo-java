package com.cykj.pos.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentPassUpdateDTO {
    @ApiModelProperty(value = "密码，即商户登录密码")
    private String password;
    @ApiModelProperty(value = "短信验证码")
    private String verifyCode;
    @ApiModelProperty(value = "身份证后6位")
    private String cardNo;
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
