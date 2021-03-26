package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * @description: 角色管理
 * @author: Tanglie
 * @time: 2021/3/22
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**根据ID查询所属于那些角色*/
    List<Integer> selectRoleListById(Integer userId);

}
