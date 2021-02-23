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
    /** 秘钥 */
    private String secretKey;
    /**交易类型 1 收入 2支出  ""或者null代表全部*/
    private String transType;
    /**月份*/
    private String monthly;
    /**页号*/
    private Integer pageNo;
    /**页大小*/
    private Integer pageSize;
    /**数据开始数*/
    private Integer start;
}
