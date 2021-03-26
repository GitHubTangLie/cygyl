package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 角色与菜单管理
 * @author: Tanglie
 * @time: 2021/3/23
 */

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {


    /**
     * 批量删除 角色与菜单的关系
     */
    boolean deleteByIds(Integer[] idList);

    /**
     *根据角色id查询所有的menuid
     */
    List<Integer> selectAllMenuIdById(Integer roleId);
}
