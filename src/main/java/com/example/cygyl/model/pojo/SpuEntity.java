package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cygyl.enums.SpuStatusEnum;
import lombok.*;

/**
 * @author 黎源
 * @date 2021/3/9 11:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "pro_spu")
public class SpuEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer categoryId;
    private Integer rootCategoryId;
    private Integer defaultSkuId;
    private Integer userId;
    private String title;
    private String subtitle;
    private Integer status;
    private String price;
    private String img;
    private String description;

}
