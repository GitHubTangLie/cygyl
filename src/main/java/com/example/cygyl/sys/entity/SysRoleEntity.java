package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cygyl.model.pojo.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: 角色实体
 * @author: Tanglie
 * @time: 2021/3/22
 */
@Data
@TableName("sys_role")
public class SysRoleEntity {

    /**角色ID*/
    @TableId(type = IdType.AUTO)
    private Integer roleId;
    /**角色名称*/
    private String roleName;
    /**角色描述*/
    private String remark;
    /** 修改者ID*/
    private Integer updateUserId;
    /**创建人的角色ID*/
    private Integer createUserId;
    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
    /** 删除时间*/
    private Date deleteTime;

    /**菜单ID List 存储自身的菜单ID */
    @TableField(exist = false)
    private List<Integer> menuIdList;

}
