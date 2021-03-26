package com.example.cygyl.v1.controller;

import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.core.response.UnifyWithBody;
import com.example.cygyl.core.response.UnifyWithBodyList;
import com.example.cygyl.model.dto.CategoryDto;
import com.example.cygyl.model.vo.CategoryListVo;
import com.example.cygyl.model.vo.CategoryWithSpusVo;
import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import com.example.cygyl.v1.service.CategoryService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/8 13:30
 */
@RestController
@RequestMapping("/category")
@Validated
@Api(tags = "分类管理")
public class CategoryController {

    @Autowired
    @Qualifier(value = "ICategoryServiceImpl")
    private CategoryService service;

    /**
     * 根据上下架获取所有分类
     *
     * @return
     */
    @GetMapping("/getCategorys/by/{status}")
//    @RequiresPermissions(value = "user:spu:list")
    @ApiOperation(value = "分类管理 - 列表展示(根据上下架状态)")
    @ApiImplicitParam(paramType = "query",dataType = "int",
            name = "status",value = "上下架状态",required = true)
    public UnifyWithBodyList<CategoryListVo> getCategoryList(
            @Min(message = "status最小为1", value = 0)
            @Max(message = "status最大为1", value = 1)
            @PathVariable(name = "status") Integer status,
            HttpServletRequest request) {
        List<CategoryListVo> categoryListVos = service.getCategorysByStatus(status);
        return new UnifyWithBodyList<>(categoryListVos);
    }

    /**
     * 查询某个分类下所有spu
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "分类管理 - 查询某分类下所有sku")
    @GetMapping("/getCategorysWithSpu/by/{id}")
    public UnifyWithBody<CategoryWithSpusVo> getCategoryWithSpuById(@Min(message = "id最小为1", value = 1) @PathVariable(name = "id") Integer id) {
        CategoryWithSpusVo categoryWithSpusVo = service.getCategoryWithSpuById(id);
        return new UnifyWithBody<>(categoryWithSpusVo);
    }

    /**
     * 添加一个分类
     * @param categoryDto
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "分类管理 - 添加一个分类 ")
    public UnifyResponse addCategory(@Validated({SaveValidGroup.class}) @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        return service.save(categoryDto,request);
    }

    /**
     * 修改一个分类
     * @param categoryDto
     * @param request
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "分类管理 - 修改一个分类")
    public UnifyResponse updateCategory(@Validated({UpdateValidGroup.class})@RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        return service.update(categoryDto, request);
    }

    /**
     * 上下架
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/upOrDown/{id}/{status}")
    @ApiOperation(value = "分类管理 - 上下架一个分类")
    public UnifyResponse upOrDown(
            @Min (message = "id最小为1",value = 1)@PathVariable(name = "id")Integer id,
            @Min (message = "最小为1",value = 0)@Max(message = "最大为1",value = 1) @PathVariable(name = "status")Integer status,
            HttpServletRequest request) {
        return service.upOrDown(id, status,request);
    }
}

