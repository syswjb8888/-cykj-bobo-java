package com.cykj.pos.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static String getString(double value){
        BigDecimal decimal = new BigDecimal(value);
        BigDecimal n =  decimal.setScale(2,BigDecimal.ROUND_DOWN);
        return n+"";
    }
    public static String getString(BigDecimal value){
        BigDecimal n =  value.setScale(2,BigDecimal.ROUND_DOWN);
        return n+"";
    }

    public static String getRoundString(BigDecimal value){
        BigDecimal n =  value.setScale(2,BigDecimal.ROUND_HALF_UP);
        return n+"";
    }
}
