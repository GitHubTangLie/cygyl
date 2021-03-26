package com.example.cygyl.exception;

/**
 * @author 黎源
 * @date 2021/3/8 15:44
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}
