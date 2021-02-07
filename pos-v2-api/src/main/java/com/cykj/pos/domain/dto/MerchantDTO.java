package com.cykj.pos.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "MerchantDTO(划拔回调请求参数)")
public class MerchantDTO implements Serializable {

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "商户主键")
    private Long merchId;

    @ApiModelProperty(value = "商户是否报件成功，0-等待审核中，1-成功，2-异常")
    private  String verifyStatus;

    @ApiModelProperty(value = "月份类型，'this'-本月，'last'-上月")
    private  String monthType;

    @ApiModelProperty(value = "划拔回调机器id")
    private List<Long> posIds;

    @ApiModelProperty(value = "页号")
    private Integer pageNo;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "开始页值")
    private Integer start;

}
