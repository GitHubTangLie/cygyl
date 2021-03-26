package com.example.cygyl.config;

import com.example.cygyl.sys.authRealm.AuthFilter;
import com.example.cygyl.sys.authRealm.AuthRelam;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: Shiro 配置文件
 * @author: Tanglie
 * @time: 2021/3/8
 */

@Configuration
public class ShiroConfig {


    //创建DefaultWebSecurityManager 关联UserRealm
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(AuthRelam authRelam){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRelam);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }
    //创建ShiroFilterFactoryBean
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //auth过滤
        Map<String, Filter> filters =  new HashMap<>();
        filters.put("authFilter",new AuthFilter());
        shiroFilter.setFilters(filters);


        Map<String,String> filterMap = new LinkedHashMap<>();
        //配置拦截规则
        //配置不会被拦截的信息
        filterMap.put("/sys/login","anon");
        filterMap.put("/sys/logout","anon");
        filterMap.put("/sys/weChatLogin","anon");
        //所有url都必须通过认证才可以方法
        filterMap.put("/**", "authFilter");
        //设置未授权界面
        shiroFilter.setUnauthorizedUrl("/unAuthorized");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }


    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //在lifecycleBeanPostProcessor之后加载初始化bean   @RequiresPermissions 不生效由于fillter是在spring容器中，而不是在springmvc容器中，所以不起作用。DependsOn 保证lifecycleBeanPostProcessor 在lifecycleBeanPostProcessor 前加载
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    //启用权限注解
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
