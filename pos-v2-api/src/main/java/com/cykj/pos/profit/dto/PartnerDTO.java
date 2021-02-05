package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class PartnerDTO {

    @ApiModelProperty(value = "伙伴用户主键")
    private Long parnterId;

    @ApiModelProperty(value = "伙伴名称")
    private String partnerName;

    @ApiModelProperty(value = "伙伴手机号")
    private String partnerMobile;

    @ApiModelProperty(value = "伙伴注册时间")
    private String registerTime;

    @ApiModelProperty(value = "团队本月交易额")
    private BigDecimal teamTransAmount;

    @ApiModelProperty(value = "团队上月交易额")
    private BigDecimal lastMonthTeamTransAmount;

    @ApiModelProperty(value = "团队本月报件审核通过数")
    private Integer teamActiveCounts;

    @ApiModelProperty(value = "团队上月报件审核通过数")
    private Integer lastMonthTeamActiveCounts;

    @ApiModelProperty(value = "团队本月机具激活数")
    private Integer teamActiveMachines;

    @ApiModelProperty(value = "团队上月机具激活数")
    private  Integer lastMonthteamActiveMacines;

    @ApiModelProperty(value = "伙伴总数")
    private Integer partnerCounts;

    @ApiModelProperty(value = "头像")
    private String portrait;

}
