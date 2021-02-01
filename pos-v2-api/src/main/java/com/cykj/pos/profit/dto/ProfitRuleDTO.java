package com.cykj.pos.profit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitRuleDTO {
    private String remark;
    private BigDecimal profitAmount;
    private BigDecimal profitMin;
    private BigDecimal profitMax;
    private BigDecimal profitTop;
    private String profitUnit;
}
