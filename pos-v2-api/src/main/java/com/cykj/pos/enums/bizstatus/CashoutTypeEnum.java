package com.cykj.pos.enums.bizstatus;

public enum CashoutTypeEnum {
    CASHOUT_TYPE_ALL("全部提现","11"),
    CASHOUT_TYPE_PROFIT("结算账户提现","10"),
    CASHOUT_TYPE_REWARD("奖励账户提现","01"),
    CASHOUT_TYPE_NONE("未提现","00");

    private String code;
    private String name;

    private CashoutTypeEnum(String name, String code){
        this.name = name;
        this.code = code;
    }
    public static String getName(String code){
        for(CashoutTypeEnum s: CashoutTypeEnum.values()){
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
