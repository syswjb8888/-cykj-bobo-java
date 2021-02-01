package com.cykj.framework.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.utils.sign.SignUtils;

@Component
public class CustomerApiInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Autowired
	private RuoYiConfig config;

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws IOException{
	    JSONObject jsonObject=new JSONObject();
	    String ticket=request.getParameter("ticket");
	    String sign=request.getParameter("sign");
	    String ts=request.getParameter("ts");
	  if(StringUtils.isEmpty(ticket) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(ts)){
	        jsonObject.put("success",false);
	        jsonObject.put("msg","args is empty");
	        jsonObject.put("code","200001");
	        PrintWriter printWriter=response.getWriter();
	        printWriter.write(jsonObject.toJSONString());
	        return false;
	    }

	    //如果redis存在ticket就认为是合法的请求
	    if(redisTemplate.hasKey(ticket)){
	        String values = (String)redisTemplate.opsForValue().get(ticket);
	        //判断ticket是否即将过期，进行续命操作
	        if(redisTemplate.opsForValue().getOperations().getExpire(ticket)!=-2
	        		&&redisTemplate.opsForValue().getOperations().getExpire(ticket)<20){
	           redisTemplate.opsForValue().set(ticket,values,10L,TimeUnit.MINUTES);
	        }
	        //判断是否重复访问，存在重放攻击的时间窗口期
	        if(SignUtils.getTimestamp()- Long.valueOf(ts)>60){
	            jsonObject.put("msg","Overtime to connect to server");
	            jsonObject.put("code","200002");
	            PrintWriter printWriter = response.getWriter();
	            printWriter.write(jsonObject.toJSONString());
	            return false;
	        }//验证签名
	        if(!SignUtils.checkSign(request,sign,config.getSignSecretKey())){
	            jsonObject.put("msg","sign is invalid");
	            jsonObject.put("code","200003");
	            PrintWriter printWriter=response.getWriter();
	            printWriter.write(jsonObject.toJSONString());
	            return false;
	        }
	        return true;
	    }else{
	        jsonObject.put("msg","ticket is invalid,please relogin.");
	        jsonObject.put("code","200004");
	        PrintWriter printWriter=response.getWriter();
	        printWriter.write(jsonObject.toJSONString());
	    }
	    return false;
	}
}
