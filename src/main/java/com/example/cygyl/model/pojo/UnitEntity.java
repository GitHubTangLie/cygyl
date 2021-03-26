package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/3/11 9:06
 */
@TableName(value = "pro_unit")
@Getter
@Setter
public class UnitEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
