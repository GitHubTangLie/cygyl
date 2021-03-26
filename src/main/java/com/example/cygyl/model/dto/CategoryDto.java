package com.example.cygyl.model.dto;

import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 黎源
 * @date 2021/3/11 13:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class CategoryDto {
    @Min(message = "id不能小于1", value = 1, groups = {UpdateValidGroup.class})
    @NotNull(message = "id不能为空", groups = {UpdateValidGroup.class})
    @ApiModelProperty("修改必填")
    private Integer id;
    private Integer parentId;
    @ApiModelProperty(value = "必填",required = true)
    @NotBlank(message = "名字不能为空", groups = {SaveValidGroup.class,UpdateValidGroup.class})
    private String name;
    private String description;
    @ApiModelProperty(value = "0 下架 1 上架 默认为上架")
    private Integer status;
    private Integer level;
    private String img;
    private Integer isRoot;

}
