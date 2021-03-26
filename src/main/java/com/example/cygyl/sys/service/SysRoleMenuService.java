package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/23
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {


    /**
     *   保存用户或者更新用户的权限
     * @param roleId
     * @param menuIdList
     * @return
     */
    void saveOrUpdate(Integer roleId, List<Integer> menuIdList);

    /**
     *  根据数组id批量删除角色
     * @param idList
     */
    boolean deleteByIds(Integer[] idList);

    /**
     *  根据自身roleId查询自身菜单Id
     * @param roleId
     * @return
     */
    List<Integer> selectAllMenuIdById(Integer roleId);
}
