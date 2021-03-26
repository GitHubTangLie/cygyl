package com.example.cygyl.sys.service.impl;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.sys.entity.SysMenuEntity;
import com.example.cygyl.sys.repository.SysMenuMapper;
import com.example.cygyl.sys.repository.SysUserMapper;
import com.example.cygyl.sys.service.SysMenuService;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 菜单管理实现类
 * @author: Tanglie
 * @time: 2021/3/16
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenuEntity> implements SysMenuService{

    @Autowired
    SysUserService userService;



    @Override
    public List<SysMenuEntity> getUserMenuList(Integer userId) {
        //如果是管理查询出所有的菜单
        if (userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //普通用户菜单
        List<Integer> menuByIdList = userService.selectAllMenuIdById(userId);
        return getAllMenuList(menuByIdList);
    }

    @Override
    public List<SysMenuEntity> selectListParentId(Integer parentId, List<Integer> menuIdList) {
        List<SysMenuEntity> menuList = selectListParentId(parentId);
        if (menuIdList == null){

            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList){
            if (menuIdList.contains(menu.getId())){
                userMenuList.add(menu);
            }
        }

        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> selectListParentId(Integer parentId) {
        return baseMapper.selectListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> selectNotButtonList() {
        return baseMapper.selectNotButtonList();
    }

    /**
     *   获取所有菜单列表
     * @param menuByIdList
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<Integer> menuByIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = selectListParentId(0,menuByIdList);

        //获取子菜单
        getMenuTreeList(menuList,menuByIdList);

        return menuList;
    }
    /**
     *  递归 ☆
     * @param menuList
     * @param menuByIdList
     * @return
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Integer> menuByIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList){
            if (entity.getType() == Constant.MenuType.CATALOG.getValue());{
                entity.setList(getMenuTreeList(selectListParentId(entity.getId(),menuByIdList),menuByIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }





    /**
     *   未完成
     * @param menuId
     */
    @Override
    public void delete(Integer menuId) {


    }


}
