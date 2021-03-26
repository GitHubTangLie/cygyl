package com.example.cygyl.exception;

/**
 * @description:  自定义异常类
 * @author: Tanglie
 * @time: 2021/3/9
 */
public class TokenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public TokenException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TokenException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public TokenException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public TokenException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
