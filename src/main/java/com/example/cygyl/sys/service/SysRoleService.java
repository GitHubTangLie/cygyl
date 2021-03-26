package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysRoleEntity;
import com.example.cygyl.util.PageUitls;

import java.util.List;
import java.util.Map;

/**
 * @description: 角色管理服务层接口
 * @author: Tanglie
 * @time: 2021/3/22
 */
public interface SysRoleService extends IService<SysRoleEntity>  {

    /**
     * 查询所有角色列表
     * @param params
     * @return
     */
    PageUitls selectAllRoleList(Map<String, Object> params);

    /**
     *  根据当前登录的Id 查询自身创建的Role
     * @param userId
     * @return
     */
    List<Integer> selectRoleListById(Integer userId);


    /**
     *  根据ID 批量删除数据
     * @param idList
     * @return
     */
    boolean removeByIds(Integer[] idList);

    /**
     *  根据角色名称查询
     * @param roleEntity
     * @return
     */
    SysRoleEntity selectByRoleName(SysRoleEntity roleEntity);

    /**
     *   添加角色
     * @param roleEntity
     * @return
     */
    boolean addRole(SysRoleEntity roleEntity);

    /**
     *  修改角色信息
     * @param roleEntity
     * @return
     */
    boolean updateRole(SysRoleEntity roleEntity);
}
