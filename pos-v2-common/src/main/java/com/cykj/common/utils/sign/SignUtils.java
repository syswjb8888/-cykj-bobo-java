package com.cykj.common.utils.sign;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SignUtils {

	public static long getTimestamp(){
		long timestampLong = System.currentTimeMillis();
		return timestampLong / 1000;
	}

	public static boolean checkSign(HttpServletRequest request, String sign,String signKey) {
		Boolean flag= false;
        //检查sigin是否过期
        Enumeration<?> pNames =  request.getParameterNames();
        Map<String, String> params = new HashMap<>();
        while(pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            if("sign".equals(pName)) continue;
            String pValue = (String)request.getParameter(pName);
            params.put(pName, pValue);
        }
        if(sign.equals(getSign(params, signKey))){
            flag = true;
        }
        return flag;
	}

	private static Object getSign(Map<String, String> params, String signKey) {
		return null;
	}
}
