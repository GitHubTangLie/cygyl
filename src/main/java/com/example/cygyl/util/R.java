package com.example.cygyl.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 返回数据
 * @author: Tanglie
 * @time: 2021/3/9
 */
public class R extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    //成功返回状态码
    public R(){
        put("code",200);
        put("msg","success");
    }

    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(HttpStatus code ,String msg){
        R r = new R();
        r.put("code",code.value());
        r.put("msg",msg);
        return r;
    }

    public static R ok(String msg){
        R r = new R();
        r.put("msg",msg);
        return r;
    }

    public static R ok(Map<String,Object> map){
        R r = new R();
        r.putAll(map);
        return r;
    }
    public static R ok(){
        return new R();
    }
    public R put(String key,Object value){
        super.put(key,value);
        return this;
    }

}
