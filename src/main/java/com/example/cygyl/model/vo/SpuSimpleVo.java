package com.example.cygyl.model.vo;

import com.example.cygyl.enums.SpuStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/3/9 15:25
 */
@Setter
@Getter
public class SpuSimpleVo {
    private Integer id;
    private Integer categoryId;
    private Integer defaultSkuId;
    private String title;
    private String subtitle;
    private Integer status;
    private String price;
    private String img;
    private String description;
}
