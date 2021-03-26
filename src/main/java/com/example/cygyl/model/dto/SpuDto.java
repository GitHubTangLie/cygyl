package com.example.cygyl.model.dto;

import com.example.cygyl.enums.SpuStatusEnum;
import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 黎源
 * @date 2021/3/11 16:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SpuDto {
    @NotNull(message = "id不能为空", groups = {UpdateValidGroup.class})
    @Min(value = 1, message = "id值最小为1")
    @ApiModelProperty("修改时必填，最小值为1")
    private Integer id;
    private Integer categoryId;
    private Integer rootCategoryId;
    //    @NotNull(message = "需要设置一个默认规格",groups = {SaveValidGroup.class})
    private Integer defaultSkuId;
    private Integer userId;
    @NotBlank(message = "商品名不能为空", groups = {SaveValidGroup.class})
    @ApiModelProperty("新增时必填")
    private String title;
    private String subtitle;
    private String price;
    private String img;
    private String description;
}
