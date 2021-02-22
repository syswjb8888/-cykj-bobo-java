package com.cykj.pos.profit.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageDTO {
    private Long userId;
    private List<Long> userIds;
    private String message;
    private Integer msgType;
}
