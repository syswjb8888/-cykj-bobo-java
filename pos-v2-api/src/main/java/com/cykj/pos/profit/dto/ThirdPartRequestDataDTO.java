package com.cykj.pos.profit.dto;

import com.cykj.pos.domain.BizMerchTransactions;
import lombok.Data;

import java.util.Map;

@Data
public class ThirdPartRequestDataDTO<T> {
    private String sign;
    private String timestamp;
    private String appId;
    private String type;
    private String requestId;
    private T data;
    private String version;
}
