package com.example.cygyl.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 黎源
 * @date 2021/3/17 14:18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "pro_sku_history")
public class SkuHistoryEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer skuId;
    private Integer userId;
    private Integer unitId;
    private Integer spuId;
    private BigDecimal price;
    private String specs;
    private String description;
    private Date updateTime;

    public SkuHistoryEntity(SkuEntity skuEntity) {
        this.skuId = skuEntity.getId();
        this.spuId = skuEntity.getSpuId();
        this.unitId = skuEntity.getUnitId();
        this.userId = skuEntity.getUserId();
        this.price = skuEntity.getPrice();
        this.specs = skuEntity.getSpecs();
        this.description = skuEntity.getDescription();
        this.updateTime = new Date();
    }
}
