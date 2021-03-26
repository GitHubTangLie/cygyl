package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 菜单管理实体
 * @author: Tanglie
 * @time: 2021/3/16
 */
@TableName("sys_menu")
@Data
public class SysMenuEntity implements Serializable {
    private static final Long serialVersionUID = 1l;

    /** 菜单ID*/
    @TableId
    private Integer id;
    /** 父菜单ID 一级菜单为0 */
    private Integer parentId;
    /** 父菜单名称*/
    @TableField(exist=false)
    private String parentName;
    /** 菜单名称*/
    private String name;
    /** 菜单URL*/
    private String url;
    /** 菜单权限*/
    private String perms;
    /** 菜单类型 0 为目录 1 为菜单 2为按钮*/
    private Integer type;
    /** 菜单排序*/
    private Integer orderNumber;
    /** 更新用户ID*/
    private Integer updateUserId;
    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
    /** 删除时间*/
    private Date deleteTime;


    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<?> list;
}
