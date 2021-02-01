package com.cykj.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cykj.framework.interceptor.CustomerApiInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //WebMvcConfigurer.super.addInterceptors(registry);

        //registry.addInterceptor(new CustomerApiInterceptor()).addPathPatterns("/pos/api/**");//拦截所有请求
    }

}
