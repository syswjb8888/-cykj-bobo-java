package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "TransAmountDataDTO（APP个人伙伴业绩数据模型）")
public class TransAmountDataDTO {
    /**
     * 商户月交易总额（含伙伴和商户）
     */
    @ApiModelProperty(value = "商户月交易总额（含伙伴和商户）")
    private BigDecimal monthlyTransAmount;
    /**
     * 月新增伙伴数
     */
    @ApiModelProperty(value = "月新增伙伴数")
    private Integer monthlyNewPartnerCounts;
    /**
     * 累计伙伴数
     */
    @ApiModelProperty(value = "累计伙伴数")
    private Integer totalPartnerCounts;
    /**
     * 月新增商户（商户：不包含任何子商户的商户）；
     */
    @ApiModelProperty(value = "月新增商户（商户：不包含任何子商户的商户）")
    private Integer monthlyNewMerchantCounts;
    /**
     * 累计商户
     */
    private Integer totalMerchantCounts;

    /**
     * 个人业绩：商户交易额
     */
    @ApiModelProperty(value = "商户交易额（商户：不包含任何子商户的商户）")
    private BigDecimal merchantTransAmount;

    /**
     * 伙伴业绩 (直营商户交易额)
     */
    @ApiModelProperty(value = "伙伴业绩")
    private BigDecimal partnerTransAmount;


}
