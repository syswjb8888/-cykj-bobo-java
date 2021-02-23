package com.cykj.pos.profit.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageDTO {
    private Long userId;
    private List<Long> userIds;
    /** 消息类型 */
    private Integer msgType;
    /** 主键id */
    private Long msgId;
    /** 消息接收用户 */
    private Long msgUserId;
    /** 消息内容 */
    private String msgContent;
    /** 是否已推送 */
    private Integer msgStatus;
    /** 读取状态 */
    private Integer readStatus;
    private String createTime;
    /** 消息类型 */
    private Integer adviceType;
    /**页号*/
    private Integer pageNo;
    /**页大小*/
    private Integer pageSize;
    /**数据开始数*/
    private Integer start;
}
