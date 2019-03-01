package com.byqh.sso.server.config;

import com.byqh.sso.server.listener.MySessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SsoServerConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(SsoServerConfig.class);

    //    @Bean
//    public FilterRegistrationBean urlFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new UrlFilter());//添加过滤器
//        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//        registration.addInitParameter("name", "alue");//添加默认参数
//        registration.setName("UrlFilter");//设置优先级
//        registration.setOrder(1);//设置优先级
//        return registration;
//    }

//    @Bean
//    public ServletListenerRegistrationBean<MySessionListener> globalSessionListener() {
//        LOGGER.info("注册sessionListener");
//        ServletListenerRegistrationBean<MySessionListener>
//                sessionListener = new ServletListenerRegistrationBean<>(new MySessionListener());
//        return sessionListener;
//    }

}
