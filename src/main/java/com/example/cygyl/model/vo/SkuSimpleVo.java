package com.example.cygyl.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.cygyl.enums.SkuStatusEnum;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.UnitEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/11 10:59
 */
@Getter
@Setter
public class SkuSimpleVo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer spuId;
    private Integer status;
    private BigDecimal price;
    private String specs;
    private String img;
    private String description;
    private String unit;
//    private UnitEntity unitEntity;


    public SkuSimpleVo( SkuEntity skuEntity) {
        this.id = skuEntity.getId();
        this.spuId = skuEntity.getSpuId();
        this.status = skuEntity.getStatus();
        this.price = skuEntity.getPrice();
        this.specs = skuEntity.getSpecs();
        this.img = skuEntity.getImg();
        this.description = skuEntity.getDescription();
        this.unit = skuEntity.getUnitEntity().getName();
    }
}
