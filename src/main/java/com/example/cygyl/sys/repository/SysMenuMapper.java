package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.model.pojo.BaseEntity;
import com.example.cygyl.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 菜单管理
 * @author: Tanglie
 * @time: 2021/3/16
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     *查询父级，，，
     */
    List<SysMenuEntity> selectListParentId(Integer parentId);

    /**
     *
     */
    List<SysMenuEntity> selectNotButtonList();
}
