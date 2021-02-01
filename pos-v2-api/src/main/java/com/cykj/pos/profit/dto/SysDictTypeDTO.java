package com.cykj.pos.profit.dto;

import com.cykj.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysDictTypeDTO {

    /** 字典类型 */
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典值层级码，用于地区查询，查询市级数据字典，传省级前两位；查询县市级数据字典，传市级前三位")
    private String dictLevelCode;

}
