package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MerchantRegisterDTO {

    @ApiModelProperty(value = "报件用户Id")
    private Long userId;

    /**
     * 推荐码,后台根据此推荐码获取期商户主键id
     */
    @ApiModelProperty(value = "推荐码")
    private Long merchCode;

    @ApiModelProperty(value = "报件商户名称")
    private String merchName;

    @ApiModelProperty(value = "报件商户简称")
    private String merchAbbr;

    @ApiModelProperty(value = "报件商户地址")
    private String merchAddr;

    @ApiModelProperty(value = "报件商户经营范围，参考数据字典")
    private String merchBizScope;

    @ApiModelProperty(value = "报件商户类型，参考数据字典")
    private String merchType;

    @ApiModelProperty(value = "报件商户结算银行卡开户行名称，参考数据字典")
    private String bank;

    private String bankCardno;

    private String bankCity;


}
