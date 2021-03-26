package com.example.cygyl.model.dto;

import com.example.cygyl.enums.SkuStatusEnum;
import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 黎源
 * @date 2021/3/12 13:13
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SkuDto {
    @Null(message = "新增不需要id",groups = {SaveValidGroup.class})
    @NotNull(message = "id不能为空",groups = {UpdateValidGroup.class})
    @Min(value = 1,message = "id最小为1",groups = {UpdateValidGroup.class})
    @ApiModelProperty("修改时必填，最小值为1")
    private Integer id;
    @NotNull(message = "该sku需要关联spu",groups = {SaveValidGroup.class})
    @ApiModelProperty("新增时必填")
    private Integer spuId;
    @NotNull(message = "该sku需要一个单位",groups = {SaveValidGroup.class})
    @ApiModelProperty("新增时必填")
    private Integer unitId;
    private Integer categoryId;
    private Integer rootCategoryId;
    @Null(message = "不需要status",groups = {SaveValidGroup.class,UpdateValidGroup.class})
    @ApiModelProperty("不填有默认值")
    private Integer status;
    @NotNull(message = "请输入单价", groups = {SaveValidGroup.class})
    @DecimalMin(value = "0",message = "单价不能小于0",groups = {SaveValidGroup.class})
    @ApiModelProperty("新增时必填，最小为0")
    private BigDecimal price;
    @NotBlank(message = "请输入规格",groups = {SaveValidGroup.class})
    @Null(message = "不能修改规格，规格为唯一标识",groups = {UpdateValidGroup.class})
    @ApiModelProperty("新增时必填")
    private String specs;
    private String description;
    private String img;
}
