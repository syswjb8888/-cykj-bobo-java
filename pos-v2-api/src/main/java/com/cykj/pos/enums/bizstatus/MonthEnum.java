package com.cykj.pos.enums.bizstatus;

public enum MonthEnum {
    JANUARY("JANUARY","01"),
    FEBRUARY("FEBRUARY","02"),
    MARCH("MARCH","03"),
    APRIL("APRIL","04"),
    MAY("MAY","05"),
    JUNE("JUNE","06"),
    JULY("JULY","07"),
    AUGUST("AUGUST","08"),
    SEPTEMBER("SEPTEMBER","09"),
    OCTOBER("OCTOBER","10"),
    NOVEMBER("NOVEMBER","11"),
    DECEMBER("DECEMBER","12");

    private String code;
    private String name;
    private MonthEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public static String getName(String code){
        for(MonthEnum s: MonthEnum.values()){
            if(code.equals(s.getCode())){
                return s.name;
            }
        }
        return "";
    }
    public static String getCode(String name){
        for(MonthEnum s: MonthEnum.values()){
            if(name.equals(s.getName())){
                return s.code;
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
