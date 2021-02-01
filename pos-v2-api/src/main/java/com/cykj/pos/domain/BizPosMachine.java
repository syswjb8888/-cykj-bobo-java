package com.cykj.pos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
 * 终端设备信息对象 biz_pos_machine
 *
 * @author cykj
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_pos_machine")
public class BizPosMachine implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "pos_id")
    @ApiModelProperty(value = "终端主键Id")
    private Long posId;

    /** 终端名称 */
    @Excel(name = "终端名称")
    @ApiModelProperty(value = "终端名称")
    private String posName;

    /** 终端SN系列号 */
    @Excel(name = "终端SN系列号")
    @ApiModelProperty(value = "终端SN系列号")
    private String posCode;

    /** 终端类型 */
    @Excel(name = "终端品牌类型",dictType = "pos_type")
    @ApiModelProperty(value = "终端品牌类型")
    private String posType;

    /** 终端设备激活码 */
    @Excel(name = "终端设备激活码")
    @ApiModelProperty(value = "终端设备激活码")
    private String posActivateCode;

    /** 终端状态 */
    @Excel(name = "终端激活状态",dictType = "pos_activate_status")
    @ApiModelProperty(value = "终端激活状态，0-未激活，1-已激活")
    private String posActivateStatus;

    /** 绑定激活时间 */
    @Excel(name = "绑定激活时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "绑定激活时间")
    private Date posBindTime;

    /** 终端活动 */
    @Excel(name = "终端活动")
    @ApiModelProperty(value = "终端活动类型，1-传统POS刷够返回活动")
    private String posActivity;

    /** 终端压金 */
    @Excel(name = "终端压金")
    @ApiModelProperty(value = "压金")
    private BigDecimal posDeposit;

    /** 终端返现 */
    @Excel(name = "终端返现")
    @ApiModelProperty(value = "返现")
    private BigDecimal posCashback;

    /** 刷返够模式 */
    @Excel(name = "刷返够模式")
    @ApiModelProperty(value = "刷返够模式，1-返合方")
    private String posModel;

    /** 终端机归属商户 */
    @Excel(name = "终端机归属商户")
    @ApiModelProperty(value = "终端机归属商户主键")
    private Long merchId;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Long createBy;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Long updateBy;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /** $column.columnComment */
    @Excel(name = "机具类型")
    @ApiModelProperty(value = "机具类型，1-传统POS机，2-电签POS机")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "商户编号 ")
    @ApiModelProperty(value = "商户编号")
    private String var2;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var3;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var4;

    /** $column.columnComment */
    @ApiModelProperty(hidden = true)
    private String var5;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Map<String, Object> params = new HashMap<>();
}
