package com.cykj.pos.domain.dto;

import lombok.Data;

@Data
public class IntegralDTO {
    /**用户id*/
    private Long userId;
    /** 加密后的通用积分 */
    private String integral;
    /** 加密后的活动积分 */
    private String activityIntegral;
    /**交易类型 收入 支出  全部*/
    private String transType;
    /**月份*/
    private String monthly;
}
