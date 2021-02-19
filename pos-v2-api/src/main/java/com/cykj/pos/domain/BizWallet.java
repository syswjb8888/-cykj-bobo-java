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
 * 我的钱包对象 biz_wallet
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_wallet")
public class BizWallet implements Serializable {

private static final long serialVersionUID=1L;


    /** 钱包主键 */
    @TableId(value = "wallet_id")
    @ApiModelProperty(value = "我的钱包主键")
    private Long walletId;

    /** 钱包用户 */
    @ApiModelProperty(value = "钱包所属用户")
    private Long userId;

    /** 结算账户总额 */
    @ApiModelProperty(value = "结算账户总额")
    private String profitAmount;

    /** 结算帐户应纳税金额 */
    @ApiModelProperty(value = "结算帐户应纳税金额")
    private BigDecimal profitTaxAmount;

    /** 奖励账户总额 */
    @ApiModelProperty(value = "奖励账户总额")
    private String rewardAmount;

    /** 奖励账户应纳税金额 */
    @ApiModelProperty(value = "奖励账户应纳税总额")
    private BigDecimal rewordTaxAmount;

    /** 钱包总额 */
    @ApiModelProperty(value = "钱包总额")
    private String walletAmount;

    /** 是否提现，00-未提现，01-奖励账户提现，10-结算账户提出，11-全部提现 */
    @ApiModelProperty(value = "是否提现")
    private String cashoutStatus;

    /** 支付密码 */
    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    /** 秘钥 */
    @ApiModelProperty(value = "秘钥")
    private String secretKey;

    /** 加密后的积分 */
    @ApiModelProperty(value = "积分")
    private String integral;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Long updateBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    private String var1;

    /** $column.columnComment */
    private String var2;

    /** $column.columnComment */
    private String var3;

    /** $column.columnComment */
    private String var4;

    /** $column.columnComment */
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
