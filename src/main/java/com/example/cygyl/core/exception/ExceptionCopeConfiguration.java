package com.example.cygyl.core.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * @author 黎源
 * @date 2021/3/2 17:44
 */
/**
 * 读取异常配置文件
 */
@PropertySource(value = "classpath:exception/exception-code.properties")
@ConfigurationProperties(prefix = "exception")
@Component
public class ExceptionCopeConfiguration {
    private Map<Integer, String> codes = new HashMap<>();

    public String getMessage(int code){
        return codes.get(code);
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }
}
