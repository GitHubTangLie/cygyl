package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @description: 用户管理mapper接口
 * @author: TangLie
 * @time: 2021/3/8
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 根据用户电话号码查询用户信息
     */
    SysUserEntity selectByPhone(String phone);

    /**
     *  查询用户的所有权限
     */
    List<String> selectAllPermission(Integer userId);

    /**
     *  查询用户的所有菜单ID
     */
    List<Integer> selectAllMenuIdById(Integer userId);

    /**
     * 根据用户名查询系统的用户
     */
    SysUserEntity selectByName(String username);

    /**
     *   修改用户的信息,以及所属角色信息
     */
    int updateUserInfo(SysUserEntity userEntity);
}
