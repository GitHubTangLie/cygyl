package com.example.cygyl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.sys.entity.SysRoleMenuEntity;
import com.example.cygyl.sys.repository.SysRoleMenuMapper;
import com.example.cygyl.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 角色与菜单的关系
 * @author: Tanglie
 * @time: 2021/3/23
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenuEntity> implements SysRoleMenuService {


    /**
     *        保存或更新菜单
     * @param roleId
     * @param menuIdList
     */
    @Override
    public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
        //删除原有的菜单信息
        this.deleteByIds(new Integer[]{roleId});
        if (menuIdList == null || menuIdList.size() == 0 ){
            return;
        }

        //保存角色与菜单
        for (Integer menuId : menuIdList){
            SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);

            // 保存
           this.save(roleMenuEntity);

        }
    }

    /**
     * 批量删除角色
     * @param idList
     * @return
     */
    @Override
    public boolean deleteByIds(Integer[] idList ) {

        return baseMapper.deleteByIds(idList);
    }

    /**
     * 根据角色id查询所有的menuid
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> selectAllMenuIdById(Integer roleId) {

        return baseMapper.selectAllMenuIdById(roleId);
    }
}
