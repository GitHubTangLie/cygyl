package com.example.cygyl.core.exception;

import io.swagger.models.auth.In;

/**
 * @description: 自定义异常处理
 * @author: Tanglie
 * @time: 2021/3/23
 */
public class CustomRunTimeException extends RuntimeException {
    //运行时异常代码
    private int conde = 500;
    private String msg;

    public CustomRunTimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomRunTimeException(String msg,Throwable throwable){

        super(msg,throwable);
        this.msg = msg;
    }

    public CustomRunTimeException(int code,String msg){
        super(msg);
        this.msg = msg;
        this.conde = code;
    }

    public CustomRunTimeException(int conde,String msg ,Throwable throwable){
        super(msg,throwable);
        this.msg = msg;
        this.conde = conde;
    }


    public int getConde() {
        return conde;
    }

    public String getMsg() {
        return msg;
    }

    public void setConde(int conde) {
        this.conde = conde;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
