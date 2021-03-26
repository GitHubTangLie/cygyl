package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 角色与用户的关系
 * @author: Tanglie
 * @time: 2021/3/23
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

    /**
     *  批量删除用户和角色的关系
     */
    boolean deleteByIds(Integer[] idList);

}
