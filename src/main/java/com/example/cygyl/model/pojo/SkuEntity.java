package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cygyl.enums.SkuStatusEnum;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author 黎源
 * @date 2021/3/11 8:58
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pro_sku")
public class SkuEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer unitId;
    private Integer spuId;
    private Integer userId;
    private Integer categoryId;
    private Integer rootCategoryId;
    private Integer status;
    private BigDecimal price;
    private String specs;
    private String description;
    private String img;

    @TableField(exist = false)
    private UnitEntity unitEntity;

}
