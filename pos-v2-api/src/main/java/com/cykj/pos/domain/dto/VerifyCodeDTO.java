package com.cykj.pos.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "短信验证码数据传输对象")
public class VerifyCodeDTO {
    @ApiModelProperty(value = "商户主键Id")
    private Long second;
}
