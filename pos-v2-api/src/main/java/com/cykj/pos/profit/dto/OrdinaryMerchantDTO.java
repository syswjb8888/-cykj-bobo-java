package com.cykj.pos.profit.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrdinaryMerchantDTO {

    @ApiModelProperty(value = "机具SN系列号")
    private String snCode;

    @ApiModelProperty(value = "商户名称")
    private String merchName;

    @ApiModelProperty(value = "商户手机号，带脱密符")
    private String markedMobile;

    @ApiModelProperty(value = "机具激活状态")
    private String activateStatus;
}
