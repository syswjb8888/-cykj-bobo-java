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
 * 终端设备状态记录对象 biz_pos_machine_status_records
 *
 * @author weijianbo
 * @date 2021-02-02
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_pos_machine_status_records")
public class BizPosMachineStatusRecords implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键标识 */
    @TableId(value = "id")
    private Long id;

    /** 商户标识 */
    @Excel(name = "商户标识")
    private String merchantId;

    /** 商户终端ID */
    @Excel(name = "商户终端ID")
    private String terminalId;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phoneNo;

    /** 绑定时间 */
    @Excel(name = "绑定时间")
    private String time;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCardNo;

    /** 激活状态 */
    @Excel(name = "激活状态")
    private String isActivated;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String receiptType;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 设备SN号 */
    @Excel(name = "设备SN号")
    private String snCode;

    /** 激活时间 */
    @Excel(name = "激活时间")
    private String activeTime;

    /** 交易编号 */
    @Excel(name = "交易编号")
    private String idTxn;

    /** 直属机构号 */
    @Excel(name = "直属机构号")
    private String directlyOrgId;

    /** 二级机构号 */
    @Excel(name = "二级机构号")
    private String secondOrgId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderId;

    /** 接收数据时间 */
    @Excel(name = "接收数据时间")
    private String receiptDate;

    /** 绑定状态 */
    @Excel(name = "绑定状态")
    private String status;

    /** 记录类型 1-绑卡 2-激活 */
    @Excel(name = "记录类型")
    private String recordsType;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
