package com.cykj.pos.enums.bizstatus;

public enum BizStatusContantEnum {
    BIZ_SUCCESS("业务操作成功","000000"),
    BIZ_FAIL("业务操作失败","999999"),
    //短信验证码相关状态
    SMS_SUCCESS("短信验证码校验通过","300000"),
    SMS_FAIL("短信验证码发送失败","300001"),
    SMS_DURATION_EXPIRED("短信验证码已超过5分钟而失效","300002"),
    SMS_FAIL_FREQUENCY("短信验证码发送过于频繁","300003"),
    SMS_INVALIDATED("短信验证码错误或已失效","300003"),
    //伙伴注册相关状态
    PARTNER_REGISTER_SUCCESS("伙伴注册失败","400000"),
    PARTNER_IS_NULL("邀请码不能为空","400001"),
    MOBILE_IS_NULL("手机号不能为空","400002"),
    PASSWORD_IS_NULL("密码不能为空","400003"),
    PARTNER_REGISTER_FAIL("伙伴注册失败","400004"),
    PARTNER_REGISTER_FINISHED("伙伴已注册，请不要重复注册","400005"),
    PARTNER_REGISTER_INVITOR_ISNOTEXIST("邀请人不存在","400006"),
    PROFIT_SETTLE_CONDITIONS_IS_NULL("请选择要计算的年份和月份!","600001"),
    PROFIT_SETTLE_DURATION_ERROR("不能计算当月或之后月份的分润!","600002"),
    PROFIT_SETTLE_HAVE_SETTLED("所选计算月份的所有商户的分润计算已经完成，请不要重复计算!","600003"),
    // 修改支付密码状态
    PAYMENT_CARDNO_ERROR("身份证验证失败","700001"),
    // 修改支付密码状态
    PAYMENT_VALIDATE_SUCCESS("支付验证成功","700002"),
    // 修改支付密码状态
    CARD_NO_IS_NULL("身份证为空，请上传","700003"),
    // 原支付密码输入有误
    PAYMENT_ORIGINAL_PASSWORD_ERROR("原支付密码输入有误","700004");
    private String code;
    private String name;

    private BizStatusContantEnum(String name, String code){
        this.name = name;
        this.code = code;
    }
    public static String getName(String code){
        for(BizStatusContantEnum s: BizStatusContantEnum.values()){
            if(code.equals(s.getCode())){
                return s.name;
            }
        }
        return "";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
