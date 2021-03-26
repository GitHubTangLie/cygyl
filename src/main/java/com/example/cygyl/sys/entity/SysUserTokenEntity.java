package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 用户Token实体表
 * @author: Tanglie
 * @time: 2021/3/9
 */
@Data
@TableName("sys_user_token")
public class SysUserTokenEntity {

    //用户ID
    @TableId(type = IdType.INPUT)
    private Integer userId;

    //token
    private String token;
    //生成时间
    private Date updateTime;
    //过期时间
    private Date expireTime;

}
