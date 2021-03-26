package com.example.cygyl.sys.form;

import lombok.Data;

/**
 * @description: 请求携带的头
 * @author: Tanglie
 * @time: 2021/3/10
 */

@Data
public class SysLoginForm {

    private String username;
    private String password;
    private String phone;
    private String uuid;

}
