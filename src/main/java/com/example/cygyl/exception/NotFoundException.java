package com.example.cygyl.exception;

/**
 * @author 黎源
 * @date 2021/3/8 15:41
 */
public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.code = code;
        this.httpStatusCode = 404;
    }
}
