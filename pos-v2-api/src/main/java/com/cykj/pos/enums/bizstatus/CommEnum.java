package com.cykj.pos.enums.bizstatus;

/**
 * 常量枚举
 */
public enum CommEnum {
    /**
     * 腾讯秘钥ID
     */
    SECRETID("secretId","AKIDvnxzoQIz8FAe2BfJHHRFo8Fn45rMAdQz"),
    /**
     * 腾讯秘钥KEY
     */
    SECRETKEY("secretKey","UouqnWDf2A1cyKn46tQm00whEYvDJI2Z"),
    /**
     * 存储桶名称
     */
    BUCKETNAME("bucketName","cykj-1303987307");
    private String key;
    private String value;
    private CommEnum(String key,String value){
        this.key = key;
        this.value = value;
    }

    public static String getName(String code){
        for(CommEnum s: CommEnum.values()){
            if(code.equals(s.getKey())){
                return s.key;
            }
        }
        return "";
    }
    public static String getCode(String name){
        for(CommEnum s: CommEnum.values()){
            if(name.equals(s.getValue())){
                return s.value;
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
