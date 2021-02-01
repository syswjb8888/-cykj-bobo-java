package com.cykj.pos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.cykj.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 离用户消息对象 biz_message_records
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_message_records")
public class BizMessageRecords implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键id */
    @TableId(value = "msg_id")
    private Long msgId;

    /** 消息接收用户 */
    @Excel(name = "消息接收用户")
    private Long msgUserId;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msgContent;

    /** 是否已推送 */
    @Excel(name = "是否已推送")
    private Integer msgStatus;

    /** $column.columnComment */
    private Date createTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
