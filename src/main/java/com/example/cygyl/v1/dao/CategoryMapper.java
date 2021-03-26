package com.example.cygyl.v1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.model.pojo.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/3/8 13:35
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

    @Update("update pro_category pc set pc.`status` = #{status} where pc.id = #{id} ")
    int updateStatusById(@Param("id") int id, @Param("status") int status);
}
