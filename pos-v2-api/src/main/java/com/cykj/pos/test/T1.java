package com.cykj.pos.test;

import java.math.BigDecimal;
import java.util.Date;

public class T1 {
    public static void main(String[] args) {
        String str="123.456";
        BigDecimal bigDecimal = new BigDecimal(str);
        System.out.println(bigDecimal);
    }
}
