package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/23
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity {

    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 角色ID */
    private Integer roleId;
    /** I菜单D */
    private Integer menuId;

}
