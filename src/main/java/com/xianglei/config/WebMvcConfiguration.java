package com.xianglei.config;

import com.xianglei.interceptor.WebRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 * Url 拦截所有请求
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getWebInterceptor() {
        return new WebRequestInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getWebInterceptor()).addPathPatterns("/**");
    }
}
