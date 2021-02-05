package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "HomePageDataDTO（APP首页数据模型）")
public class HomePageDataDTO implements Serializable {

    /**
     * 商户月交易总额（含伙伴和商户）
     */
    @ApiModelProperty(value = "商户月交易总额（含伙伴和商户）")
    private BigDecimal monthlyTransAmount;
    /**
     * 月新增伙伴数（伙伴：包含子商户的商户）
     */
    @ApiModelProperty(value = "月新增伙伴数（伙伴：包含子商户的商户）")
    private Integer monthlyNewPartnerCounts;
    /**
     * 月新增商户（商户：不包含任何子商户的商户）；
     */
    @ApiModelProperty(value = "月新增商户（商户：不包含任何子商户的商户）")
    private Integer monthlyNewMerchantCounts;
    /**
     * 新闻快讯（通知公告中配置）
     */
    @ApiModelProperty(value="新闻快讯（通知公告中配置）")
    private String expressNews;
    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;
    /**
     * 商户编号
     */
    @ApiModelProperty(value="商户编号")
    private String merchCode;
    /**
     * 腾讯秘钥ID
     */
    @ApiModelProperty(value="腾讯秘钥ID")
    private String secretId;
    /**
     * 腾讯秘钥key
     */
    @ApiModelProperty(value="腾讯秘钥key")
    private String secretKey;
    /**
     * 存储桶名称
     */
    @ApiModelProperty(value="存储桶名称")
    private String bucketName;
    /**
     * 头像URL
     */
    @ApiModelProperty(value="头像url")
    private String portrait;
}
