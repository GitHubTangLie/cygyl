package com.example.cygyl.v1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.pojo.UnitEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/11 9:17
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuEntity> {



    @Select("select * from pro_sku ps \n" +
            "left join pro_unit pu on ps.unit_id = pu.id\n" +
            "where ps.spu_id = #{spuId} and ps.status = #{status}")
    @Results(
            @Result(column = "unit_id",property = "unitEntity",javaType = UnitEntity.class,
            one=@One(select = "com.example.cygyl.v1.dao.UnitMapper.getById")
            )
    )
    List<SkuEntity> getSkusBySpuId(@Param("spuId") Integer spuId,
                                   @Param("status") Integer status);

    @Select("select * from pro_sku ps \n" +
            "left join pro_unit pu on ps.unit_id = pu.id\n" +
            "where ps.status = #{status}")
    @Results(
            @Result(column = "unit_id",property = "unitEntity",javaType = UnitEntity.class,
                    one=@One(select = "com.example.cygyl.v1.dao.UnitMapper.getById")
            )
    )
    Page<SkuEntity> getSkusByStatus(IPage page, @Param("status")Integer status);


    @Update("update pro_sku ps set ps.category_id = #{cid} where ps.spu_id = #{pid} ")
    int updateForSpu(@Param("pid") int pid,@Param("cid") int cid);

    @Update("update pro_sku ps set ps.`status` = #{status} where ps.id = #{id} ")
    int updateStatusById(@Param("id") int id, @Param("status") int status);
}
