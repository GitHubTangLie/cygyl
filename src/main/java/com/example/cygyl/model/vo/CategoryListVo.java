package com.example.cygyl.model.vo;

import com.example.cygyl.enums.CategoryStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/3/8 17:16
 */
@Getter
@Setter
public class CategoryListVo {
    private Integer id;
    private String name;
    private String description;
    private Integer status;
    private Integer level;
}
