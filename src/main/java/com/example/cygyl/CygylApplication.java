package com.example.cygyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@MapperScan("com.example.cygyl.sys.repository")
public class CygylApplication{

    public static void main(String[] args) {
        SpringApplication.run(CygylApplication.class, args);
    }

}
