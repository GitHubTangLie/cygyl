package com.example.cygyl.sys.controller;

import com.example.cygyl.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 公共属性
 * @author: Tanglie
 * @time: 2021/3/11
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser(){

        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Integer getUserId(){

        return getUser().getId();
    }
}
