package com.example.cygyl.model.vo;

import com.example.cygyl.util.BeanMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/17 11:14
 */
@Getter
@Setter
public class SpuSimpleVoWithSkus {
    private Integer id;
    private Integer categoryId;
    private Integer defaultSkuId;
    private String title;
    private String subtitle;
    private Integer status;
    private String price;
    private String img;
    private String description;
    private List<SkuSimpleVo> skuSimpleVos;
}
