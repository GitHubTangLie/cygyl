package com.example.cygyl.core.response;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2021/3/22 17:06
 */
@Setter
@Getter
public class UnifyWithBody<T>{
    private Integer code;
    private String message;
    private T body;
    public UnifyWithBody(T body) {
        this.body = body;
        this.code = 200;
        this.message = "成功";
    }
}
