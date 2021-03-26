package com.example.cygyl.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 系统用户token
 * @author: Tanglie
 * @time: 2021/3/9
 */

@Mapper
public interface SysUserTokenMapper extends BaseMapper<SysUserTokenEntity> {

    /**
     *  查询token
     */
    SysUserTokenEntity selectByToken(String token);
}
