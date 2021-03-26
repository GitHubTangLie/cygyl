package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cygyl.enums.CategoryStatusEnum;
import lombok.*;

/**
 * @author 黎源
 * @date 2021/3/8 13:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "pro_category")
public class CategoryEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;
    private String name;
    private String description;
    private Integer status;
    private Integer level;
    private String img;
    private Integer isRoot;

}
