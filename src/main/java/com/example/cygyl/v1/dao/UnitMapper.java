package com.example.cygyl.v1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.model.pojo.UnitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 黎源
 * @date 2021/3/11 10:18
 */
@Mapper
public interface UnitMapper extends BaseMapper<UnitEntity> {
    @Select("select * from pro_unit where id = #{id}")
    UnitEntity getById(@Param("id") int id );
}
