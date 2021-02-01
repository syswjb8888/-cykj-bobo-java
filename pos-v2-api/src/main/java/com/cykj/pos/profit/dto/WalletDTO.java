package com.cykj.pos.profit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDTO {

    @ApiModelProperty(value = "钱包所属用户主键")
    private Long userId;

    @ApiModelProperty(value = "钱包总额")
    private BigDecimal walletAmount;

    @ApiModelProperty(value = "结算帐户总额")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "奖励帐户总额")
    private BigDecimal rewardAmount;

    @ApiModelProperty(value = "结算帐户应纳税总额")
    private BigDecimal profitTaxAmount;

    @ApiModelProperty(value = "奖励账户应纳税总额")
    private BigDecimal rewardTaxAmount;

    @ApiModelProperty(value = "身份证件号码后6位")
    private String idCard;

    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    @ApiModelProperty(value = "短信验证码")
    private String verifyCode;

    @ApiModelProperty(value = "提现类型，01-奖励账户提现，10-结算账户提现，11-全部提出")
    private String cashoutType;

}
