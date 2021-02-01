package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserDTO implements Serializable {
    @ApiModelProperty(value = "登录用户主键Id")
    private  Long userId;
}
