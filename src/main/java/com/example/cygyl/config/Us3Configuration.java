package com.example.cygyl.config;

import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 黎源
 * @date 2021/3/15 9:33
 */
@Configuration
public class Us3Configuration {

    @Value("${us3.publicKey}")
    public String publicKey;
    @Value("${us3.privateKey}")
    public String privateKey;
    @Value("${us3.region}")
    public String region;


    @Bean
    public ObjectAuthorization objectAuthorization() {
        return new UfileObjectLocalAuthorization(publicKey,privateKey);
    }

    @Bean
    public ObjectConfig objectConfig() {
        return new ObjectConfig(region,"ufileos.com");
    }
}
