package com.cykj.pos.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MerchantDict {
    /**
     *用户主键
     */
    @ApiModelProperty(value="用户主键Id")
    private Long userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private Long nickName;

    /**
     * 用户手机号码
     */
    @ApiModelProperty(value="用户手机号码")
    private String mobile;

    /**
     * 商户id
     */
    @ApiModelProperty(value="商户主键Id")
    private Long dictValue;
    /**
     * 商户名称
     */
    @ApiModelProperty(value="商户名称")
    private String dictLabel;
    /**
     * 数据索引key
     */
    @ApiModelProperty(value="数据索引key")
    private int id;

    @ApiModelProperty(value = "头像")
    private String portrait;
}
