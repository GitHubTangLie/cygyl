package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cygyl.model.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;
import java.util.List;

/**
 * @description: 用户表的实体
 * @author: TangLie
 * @time: 2021/3/8
 */
@Getter
@Setter
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity {

    /** 用户ID*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户姓名*/
    private String username;
    /** 用户密码*/
    private String password;
    /** 用户电话*/
    private String phone;
    /** 盐*/
    private String salt;
    /** 用户邮箱*/
    private String email;
    /** 创建者ID*/
    private Integer createUserId;
    /** 修改者ID*/
    private Integer updateUserId;
    /** 用户状态*/
    private  Integer status;
    /** 所属组织*/
    private Integer companyId;
    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
    /** 删除时间*/
    private Date deleteTime;

    /** 用户角色ID列表,可以拥有多个角色*/
    @TableField(exist = false)
    private List<Integer> roleIdList;

}
