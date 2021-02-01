package com.cykj.pos.profit.dto;

import lombok.Data;

@Data
public class KuaiQianResult {
    private String rspCode;
    private String rspMsg;

    public KuaiQianResult(){}
    public KuaiQianResult(String rspCode,String rspMsg){
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
    }
}
