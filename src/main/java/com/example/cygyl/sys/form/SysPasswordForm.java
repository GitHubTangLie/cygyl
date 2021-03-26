package com.example.cygyl.sys.form;

import lombok.Data;

/**
 * @description:  提交密码的表单
 * @author: Tanglie
 * @time: 2021/3/18
 */
@Data
public class SysPasswordForm {

    /**新密码*/
    private String newPassword;

    /**旧密码*/
    private String oldPassword;

}
