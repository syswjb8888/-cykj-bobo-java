package com.cykj.pos.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePortraitDTO {
    @ApiModelProperty(value = "头像URL")
    private String portraitUrl;
}
