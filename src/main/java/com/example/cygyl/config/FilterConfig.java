package com.example.cygyl.config;

import com.example.cygyl.sys.common.xssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

/**
 * @description: Filter配置
 * @author: Tanglie
 * @time: 2021/3/11
 */

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean shiroFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
            //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registrationBean.addInitParameter("targetFilterLifecycle","true");
        registrationBean.setEnabled(true);
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean xxsFilterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new xssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);

        return registration;
    }
}
