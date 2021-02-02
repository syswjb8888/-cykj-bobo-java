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
 * 商户交易记录对象 biz_merch_transactions
 *
 * @author ningbingwu
 * @date 2021-01-29
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_merch_transactions")
public class BizMerchTransactions implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 交易主键
     */
    @TableId(value = "trn_id")
    private Long trnId;

    /**
     * 邀请码
     */
    @Excel(name = "邀请码")
    private String innerMerchCode;

    /**
     * 用户商编
     */
    @Excel(name = "用户商编")
    private String merchFlagCode;

    /**
     * 外部订单号
     */
    @Excel(name = "外部订单号")
    private String orderId;

    /**
     * 交易金额（元）
     */
    @Excel(name = "交易金额", readConverterExp = "元=")
    private BigDecimal transAmount;

    /**
     * 交易编号
     */
    @Excel(name = "交易编号")
    private String idTxn;

    /**
     * 产品码
     */
    @Excel(name = "产品码")
    private String productCode;

    /**
     * 优惠费率标识
     */
    @Excel(name = "优惠费率标识")
    private String discountRateFlag;

    /**
     * 交易状态
     */
    @Excel(name = "交易状态")
    private String status;

    /**
     * 错误码
     */
    @Excel(name = "错误码")
    private String errorCode;

    /**
     * 错误描述
     */
    @Excel(name = "错误描述")
    private String errorMsg;

    /**
     * 交易请求时间
     */
    @Excel(name = "交易请求时间")
    private String transDate;

    /**
     * 输入模式
     */
    @Excel(name = "输入模式")
    private String serviceEntryMode;

    /**
     * 卡类型
     */
    @Excel(name = "卡类型")
    private String cardType;

    /**
     * 设备SN号
     */
    @Excel(name = "设备SN号")
    private String posCode;

    /**
     * 设备类型
     */
    @Excel(name = "设备类型")
    private String deviceType;

    /**
     * 商户实名姓名
     */
    @Excel(name = "商户实名姓名")
    private String userName;

    /**
     * 商户手机号
     */
    @Excel(name = "商户手机号")
    private String phoneNo;

    /**
     * 区分智能收款和店面收银两种方式的标识
     */
    @Excel(name = "区分智能收款和店面收银两种方式的标识")
    private String isStoreCashier;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String receiptType;

    /**
     * S0手续费套餐生效标识
     */
    @Excel(name = "S0手续费套餐生效标识")
    private String s0PackageFlag;

    /**
     * 二级机构编号
     */
    @Excel(name = "二级机构编号")
    private String secondOrgId;

    /**
     * 直属机构编号
     */
    @Excel(name = "直属机构编号")
    private String directlyOrgId;

    /**
     * 一机双模标识
     */
    @Excel(name = "一机双模标识")
    private String specialMode;

    /**
     * e-sim卡服务费标识
     */
    @Excel(name = "e-sim卡服务费标识")
    private String esimFlag;

    /**
     * 用户实名身份证号
     */
    @Excel(name = "用户实名身份证号")
    private String idCardNo;

    /**
     * 交易上送商户编号
     */
    @Excel(name = "交易上送商户编号")
    private String authMerchId;

    /**
     * 授权商户编号
     */
    @Excel(name = "授权商户编号")
    private String authMerchantId;

    /**
     * 交易上送终端编号
     */
    @Excel(name = "交易上送终端编号")
    private String authTerminalId;

    /**
     * 交易方式
     */
    @Excel(name = "交易方式")
    private String tradeType;

    /**
     * 银行授权码
     */
    @Excel(name = "银行授权码")
    private String bankAuthCode;

    /**
     * 店面收银交易标识
     */
    @Excel(name = "店面收银交易标识")
    private String storeCachierFlag;

    /**
     * 店面收银交易标识
     */
    @Excel(name = "交易状态")
    private String transactionStatus;

    /**
     * 原交易外部订单号
     */
    @Excel(name = "原交易外部订单号")
    private String originOutTraceNo;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
