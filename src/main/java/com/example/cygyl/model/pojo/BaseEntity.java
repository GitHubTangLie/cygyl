package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BaseEntity {
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
}
