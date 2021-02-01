package com.cykj.pos.util;

import java.util.Random;

public class VerifyCodeUtils {

    public static String getVerifyCode(){
        //生成6位验证码
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }
}
