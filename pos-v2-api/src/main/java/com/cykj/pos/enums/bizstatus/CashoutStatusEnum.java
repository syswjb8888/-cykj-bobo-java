package com.cykj.pos.enums.bizstatus;

public enum CashoutStatusEnum {
    CASHOUT_STATUS_ALL_SUCCESS("全部提现成功","11"),
    CASHOUT_STATUS_PROFIT_SUCCESS("结算账户提现成功","10"),
    CASHOUT_STATUS_REWARD_SUCCESS("奖励账户提现成功","01"),
    CASHOUT_STATUS_ALL_FAIL("全部提现失败","22"),
    CASHOUT_STATUS_PROFIT_FAIL("结算账户提现失败","20"),
    CASHOUT_STATUS_REWARD_FAIL("奖励账户提现失败","02");

    private String code;
    private String name;

    private CashoutStatusEnum(String name, String code){
        this.name = name;
        this.code = code;
    }
    public static String getName(String code){
        for(CashoutStatusEnum s: CashoutStatusEnum.values()){
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
