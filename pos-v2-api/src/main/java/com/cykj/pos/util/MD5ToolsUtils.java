package com.cykj.pos.util;

public class MD5ToolsUtils {

    public static final String M_KEY = "M_KEY";                            //密码密匙类型
    public static final String key_board = "keyboard";                     //密码键盘密匙
    public static final String login_password_key = "login_password_key";  //登陆密码密匙
    public static final String trs_pssword_key = "trs_pssword_key";        //交易密码密匙

    /**
     * MD5加密
     * @param custNo
     * @param password
     * @return
     */
    public static String MD5(String custNo, String password, String key1){

        String key = DictUtils.getDictLabel(MD5ToolsUtils.trs_pssword_key, MD5ToolsUtils.M_KEY, "");
        return MD5Tools.MD5(custNo.trim() + password.trim() + key.trim());
    }
}
