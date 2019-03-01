package config;

import filter.SsoClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author yqi
 * @description 单点登录自动配置类
 */
@Configuration
public class SsoClientConfig {

    @Autowired
    SsoClientConfigProperties ssoClientConfigProperties;

    @Bean
    @ConditionalOnMissingBean
    public SsoClientConfigProperties ssoClientConfigProperties() {
        return new SsoClientConfigProperties();
    }

    @Bean
    public FilterRegistrationBean ssoClientFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new SsoClientFilter());
        filterBean.addUrlPatterns(ssoClientConfigProperties.getUrlPatterns());
        filterBean.setName("ssoClientFilter");
        return filterBean;
    }
}
