package com.example.cygyl.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/3/8 15:40
 */
@Getter
@Setter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;
}
