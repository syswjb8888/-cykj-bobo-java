package com.cykj.pos.test;

import com.alibaba.fastjson.JSONObject;
import com.cykj.pos.profit.dto.BizMerchTransDTO;
import com.cykj.pos.profit.dto.ThirdPartRequestDataDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class T {
    public static void main(String[] args) {
        /*Per bobo = new Per();
        bobo.setSex("男1");
        bobo.setName("魏建波1");

        String data = JSONObject.toJSONString(bobo);
        System.out.println(data);*/
        /*String str = "{\"authCode\":\"098537\",\"tradeType\":\"1\",\"serviceEntryMode\":\"071\",\"status\":\"1\",\"discountRateFlag\":\"0\",\"idTxn\":\"72000000010548070\",\"errorMsg\":\"交易成功\",\"cardType\":\"0002\",\"amt\":\"1212.00\",\"deviceType\":\"2\",\"snCode\":\"00000302J8NL10514853\",\"errorCode\":\"00\",\"merchantId\":\"812310045112270\",\"orderId\":\"0024760414102623J8NL10514853\",\"txnTime\":\"2020-04-14 10:26:32\"}";
        str = str.replace("\\","");
        BizMerchTransDTO bizMerchTransDTO = JSONObject.parseObject(str, BizMerchTransDTO.class);
        System.out.println(str);
        System.out.println(bizMerchTransDTO);*/
        /*ThirdPartRequestDataDTO<String> aaa = new ThirdPartRequestDataDTO<>();
        Type type = ((ParameterizedType)aaa.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(type);*/
        String str = "12345678";
        System.out.println(str.substring(str.length()-6));

    }
}
class Per{
    String sex;
    String name;
    String age;
    String money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}