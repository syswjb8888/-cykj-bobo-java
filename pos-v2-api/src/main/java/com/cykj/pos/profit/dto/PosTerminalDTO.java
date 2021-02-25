package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "PosTerminalDTO(POS终端APP请求数据模型)")
public class PosTerminalDTO {

    @ApiModelProperty(value = "商户主键Id")
    private Long merchId;
    @ApiModelProperty(value = "订单主键Id")
    private Long orderId;
    @ApiModelProperty(value = "商户编号")
    private String merchCode;
    @ApiModelProperty(value = "用户主键Id，统计或划拔回调操作时用户Id")
    private Long userId;
    @ApiModelProperty(value = "划拔回调操作类型，数据字典值")
    private Integer operType;
    @ApiModelProperty(value = "终端名称")
    private String posName;
    @ApiModelProperty(value = "SN系列号")
    private String posCode;

    @ApiModelProperty(value = "区间起始SN系列号")
    private String posCodeStart;

    @ApiModelProperty(value = "区间结束SN系列号")
    private String posCodeEnd;

    @ApiModelProperty(value = "品牌类型，数据字典值")
    private String posType;
    @ApiModelProperty(value = "激活状态，数据字典值")
    private String posActivateStatus;
    @ApiModelProperty(value = "机具类型，数据字典值")
    private String posMachineType;

    @ApiModelProperty(value = "响应值，操作成功数，数据字典")
    private Integer successCounts;
    @ApiModelProperty(value = "终端主键列表，即划拔回调终端列表",reference = "BizPosMachine")
    private List<Long> posIds;
    @ApiModelProperty(value = "区间查询时的终端SN号列表",reference = "BizPosMachine")
    private List<String> posCodes;
    @ApiModelProperty(value = "响应值，商户终端总数（含伙伴和商户）")
    private Long terminalCounts;
    @ApiModelProperty(value = "响应值，商户终端激活数（含伙伴和商户）")
    private Long terminalActivatedCounts;
    @ApiModelProperty(value = "响应值，商户终端未激活数（含伙伴和商户）")
    private Long terminalNoActivateCounts;

    @ApiModelProperty(value = "操作时间，格式：2021-01-21")
    private String operateTime;

    @ApiModelProperty(value = "起始页号，页号和页大小值均为-1表示不分页")
    private Integer pageNo;
    @ApiModelProperty(value = "页大小，页号和页大小值均为-1表示不分页")
    private Integer pageSize;

}
