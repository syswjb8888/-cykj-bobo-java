package com.cykj.pos.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cykj.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "BillQueryDTO(账单查询数据模型)")
public class BillQueryDTO {
    /** 主键 */
    private Long billId;

    @ApiModelProperty(value = "商户主键Id")
    private Long merchId;

    /** 交易金额 */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    /** 账单日期 */
    @ApiModelProperty(value = "账单日期")
    private String billDate;

    @ApiModelProperty(value = "用户主键Id，统计或划拔回调操作时用户Id")
    private Long userId;

    @ApiModelProperty(value = "账单交易类型值")
    private String billTypeValue;

    @ApiModelProperty(value = "账单交易类型名称")
    private String billTypeLabel;

    @ApiModelProperty(value = "yyyy年-MM月")
    private String yearMonth;

    @ApiModelProperty(value = "起始页号，页号和页大小值均为-1表示不分页")
    private Integer pageNo;

    @ApiModelProperty(value = "页大小，页号和页大小值均为-1表示不分页")
    private Integer pageSize;

    @ApiModelProperty(value = "从第几条数据开始遍历")
    private Long start;
}
