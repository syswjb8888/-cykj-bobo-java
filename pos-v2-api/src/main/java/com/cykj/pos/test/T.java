package com.cykj.pos.test;

import com.alibaba.fastjson.JSONObject;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.profit.dto.BizMerchTransDTO;
import com.cykj.pos.profit.dto.ThirdPartRequestDataDTO;
import com.cykj.pos.util.BigDecimalUtil;
import com.cykj.pos.util.DateUtils;
import com.cykj.pos.util.OrderUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

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
        /*String str = "12345678";
        System.out.println(str.substring(str.length()-6));*/
        /*String pass = "567890";
        String p1 = SecurityUtils.encryptPassword(pass);
        System.out.println(SecurityUtils.matchesPassword(pass,p1));*/
        /*String formatedDate = DateUtils.getCaculateYearAndMonth("last","yyyyMMdd");
        System.out.println(formatedDate);*/
        BigDecimal bignum1 = new BigDecimal("10.0000");

        /*BigDecimal bignum2 = new BigDecimal("5.333");

        bignum1 = bignum1.add(bignum2);

        System.out.println(bignum1);
        System.out.println(bignum2);*/
        System.out.println(bignum1.doubleValue());
        System.out.println(Double.parseDouble(bignum1.toString()));
        // System.out.println(bignum3);
        /*System.out.println(BigDecimal.valueOf(109.2222));
        System.out.println(bignum1.stripTrailingZeros().toPlainString());*/

        /*System.out.println(BigDecimalUtil.getString(100.567));
        System.out.println(BigDecimalUtil.getString(1000.5));
        System.out.println(BigDecimalUtil.getString(0));*/

        /*BigDecimal bignum22 = new BigDecimal("600.12345678");
        BigDecimal bignum222 = bignum22.negate();
        System.out.println(bignum222);*/

        /*String pass = SecurityUtils.encryptPassword("123123");
        System.out.println(pass);*/

        /*System.out.println(UUID.randomUUID().toString().replace("-","").length());*/
        /*int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        String str = String.format("%010d", hashCodeV);
        System.out.println(str);*/
        /*LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));*/
        for(int i=0;i<10000;i++){
            System.out.println(OrderUtil.randomOrderNo());
        }
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