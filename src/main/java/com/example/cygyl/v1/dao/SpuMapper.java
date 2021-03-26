package com.example.cygyl.v1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.model.pojo.SpuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author 黎源
 * @date 2021/3/9 11:39
 */
@Mapper
public interface SpuMapper extends BaseMapper<SpuEntity> {

    @Update("update pro_spu ps set ps.`status` = #{status} where ps.id = #{id} ")
    int updateStatusById(@Param("id") int id, @Param("status") int status);
}
