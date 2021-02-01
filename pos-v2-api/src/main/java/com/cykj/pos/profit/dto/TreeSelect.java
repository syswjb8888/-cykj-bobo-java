package com.cykj.pos.profit.dto;

import com.cykj.pos.domain.BizMerchant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long merchId;

    /** 节点名称 */
    private String label;

    private Long parentId;

    private String merchCode;
    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(){}
    public TreeSelect(BizMerchant merchant){
        this.merchId = merchant.getMerchId();
        this.label = merchant.getMerchName();
        this.parentId = merchant.getParentId();
        this.merchCode = merchant.getMerchCode();
        this.children = merchant.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
