package com.cykj.pos.profit.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OperationRecordsDTO {

    @ApiModelProperty(value = "操作终端数")
    private Integer posCounts;

    @ApiModelProperty(value = "操作者Id")
    private Long operator;

    @ApiModelProperty(value = "操作者名称")
    private String operatorName;

    @ApiModelProperty(value = "找拔合作方名称")
    private String allocName;

    @ApiModelProperty(value = "回调合作方名称")
    private String adjustName;

    @ApiModelProperty(value = "操作终端SN系列号,可能包含多个，多个系列号之间用英文逗号做分隔")
    private List<String> posCodes;

    @ApiModelProperty(value = "操作时间，格式：2021-01-21 15:35:55")
    private String operateTime;
}
