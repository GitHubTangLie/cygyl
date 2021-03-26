package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/23
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {


    /**保存用户提交的角色信息*/
    void saveOrUpdate(Integer userId, List<Integer> roleIdList);

    /**删除用户与角色关系*/
    boolean deleteByIds(Integer[] idList);

}
