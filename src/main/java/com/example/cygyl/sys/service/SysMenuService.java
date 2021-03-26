package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysMenuEntity;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/16
 */
public interface SysMenuService extends IService<SysMenuEntity>{

    /**
     *  获得用户的菜单
     * @param userId
     * @return
     */
    List<SysMenuEntity> getUserMenuList(Integer userId);


    /**
     *    根据父菜单查询子菜单
     * @param parentId 父菜单
     * @param menuIdList 用户菜单Id
     * @return
     */
    List<SysMenuEntity> selectListParentId(Integer parentId,List<Integer> menuIdList);

    /**
     * 根据父菜单 查询子菜单
     * @param parentId
     * @return
     */
    List<SysMenuEntity> selectListParentId(Integer parentId);

    /**
     *   获取不包含按钮菜单的列表
     * @return
     */
    List<SysMenuEntity> selectNotButtonList();

    /**
     *  删除菜单
     * @param menuId
     */
    void delete(Integer menuId);




}
