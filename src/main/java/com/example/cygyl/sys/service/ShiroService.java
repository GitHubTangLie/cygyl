package com.example.cygyl.sys.service;

import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * @description: shiro 相关接口
 * @author: Tanglie
 * @time: 2021/3/10
 */
public interface ShiroService {


    /**
     *  token 查询
     * @param token
     * @return
     */
    SysUserTokenEntity selectByToken(String token);


    /**
     *  根据用户名查询Token
     * @param userId
     * @return SysUserEntity
     */
    SysUserEntity selectUserById(Integer userId);

    /**
     *  根据用户id 查询所拥有所有的权限
     */
    Set<String> selectUserPermission(Integer userId);
}
