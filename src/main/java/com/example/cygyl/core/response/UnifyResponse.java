package com.example.cygyl.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2021/3/2 17:49
 */

/**
 * 相应对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnifyResponse {
    private Integer code;
    private String message;
    private String request;

    public static UnifyResponse OK(HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(200)
                .message("成功")
                .request(httpRequest.getMethod()+" : "+httpRequest.getRequestURI())
                .build();
    }

    public static UnifyResponse OK(Integer code,HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(code)
                .message("成功")
                .request(httpRequest.getRequestURI())
                .build();
    }

    public static UnifyResponse OK(Integer code,String message,HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(code)
                .message(message)
                .request(httpRequest.getRequestURI())
                .build();
    }
}
