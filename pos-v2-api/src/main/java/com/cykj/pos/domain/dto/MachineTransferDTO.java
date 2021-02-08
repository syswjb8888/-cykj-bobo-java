package com.cykj.pos.domain.dto;

import com.cykj.pos.domain.BizPosMachine;
import lombok.Data;

@Data
public class MachineTransferDTO extends BizPosMachine {
    /**
     * 激活类型  2-激活
     */
    private String recordsType;
}
