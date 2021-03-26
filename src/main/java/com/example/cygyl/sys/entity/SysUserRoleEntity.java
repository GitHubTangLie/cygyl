package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户与角色的对应关系
 * @author: Tanglie
 * @time: 2021/3/23
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity implements Serializable {

    /** ID*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID*/
    private Integer userId;
    /** 角色ID*/
    private Integer roleId;

}
