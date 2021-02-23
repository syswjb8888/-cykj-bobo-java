package com.cykj.pos.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 订单工具类
 */
public class OrderUtil {
    /**
     * 生成订单号
     * @return
     */
    public static String randomOrderNo(){
        StringBuffer sb = new StringBuffer("");
        // 获得日期 年月日
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        sb.append(formatedDate);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 10 代表长度为4
        // d 代表参数为正数型
        String str = String.format("%010d", hashCodeV);
        sb.append(str);
        return sb.toString();
    }
}
