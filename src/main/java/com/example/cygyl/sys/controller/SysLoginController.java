package com.example.cygyl.sys.controller;

import com.example.cygyl.sys.entity.SysUserEntity;

import com.example.cygyl.sys.form.SysLoginForm;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.sys.service.SysUserTokenService;
import com.example.cygyl.util.R;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description: 系统登录相关
 * @author: Tanglie
 * @time: 2021/3/8
 */

@RestController
@RequestMapping("/sys")
public class SysLoginController extends AbstractController{

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserTokenService sysUserTokenService;

    @PostMapping("/login")
    public Map<String,Object> sysLogin(@RequestBody SysLoginForm form){
        System.out.println("进入登录页面");

        //判断用户是否存在
        SysUserEntity userEntity = sysUserService.selectByName(form.getUsername());
        if (userEntity == null || new Sha256Hash(form.getPassword()).equals(userEntity.getPassword())){
            return R.error(HttpStatus.BAD_REQUEST,"用户名不存在/密码错误!");
        }

        if (userEntity.getStatus() == 0){
            return R.error("账户已锁定,请联系管理员!");
        }
        //生成Token 保存到数据库
        R r = sysUserTokenService.createToken(userEntity.getId());
        return r;
    }

    @RequestMapping("/logout")
    public String sysLogout(){
        System.out.println("进入退出页面");

        return "进入退出页面";
    }

    @RequestMapping("/unAuthorized")
    public String sysunAuthorized(){
        System.out.println("进入未被授权页面");

        return "进入未被授权页面";
    }

}
