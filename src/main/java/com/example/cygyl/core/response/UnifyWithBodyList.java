package com.example.cygyl.core.response;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/22 17:54
 */
@Setter
@Getter
public class UnifyWithBodyList<T>{
    private Integer code;
    private String message;
    private List<T> body;


    public UnifyWithBodyList(List<T> body) {
        this.body = body;
        this.code = 200;
        this.message = "成功";
    }
}
