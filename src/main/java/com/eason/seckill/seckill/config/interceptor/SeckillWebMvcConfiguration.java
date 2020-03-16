package com.eason.seckill.seckill.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: eason
 * @Date: Created in 20:47 2020/3/1
 * @Description:
 */
@Configuration
public class SeckillWebMvcConfiguration implements WebMvcConfigurer{

    @Autowired
    LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login/login").excludePathPatterns("/login/dologin");
    }

}
