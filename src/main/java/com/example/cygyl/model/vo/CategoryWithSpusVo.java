package com.example.cygyl.model.vo;

import com.example.cygyl.model.pojo.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/16 13:25
 */
@Getter
@Setter
public class CategoryWithSpusVo {
    private Integer id;
    private String name;
    private List<SpuSimpleVo> spuSimpleVos;

    public CategoryWithSpusVo(CategoryEntity entity, List<SpuSimpleVo> spuSimpleVos) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.spuSimpleVos = spuSimpleVos;
    }
}
