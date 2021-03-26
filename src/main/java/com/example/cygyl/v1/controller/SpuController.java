package com.example.cygyl.v1.controller;

import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.core.response.UnifyWithBody;
import com.example.cygyl.core.response.UnifyWithBodyList;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SpuDto;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.SpuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVoWithSkus;
import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import com.example.cygyl.v1.service.SpuService;
import com.example.cygyl.v1.service.impl.SpuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * @author 黎源
 * @date 2021/3/9 11:46
 */
@RestController
@RequestMapping("/spu")
@Validated
@Api(tags = "商品管理(spu)")
public class SpuController {

    @Autowired
    private SpuServiceImpl spuServiceImpl;
    @Autowired
    @Qualifier(value = "ISpuServiceImpl")
    private SpuService service;

    /**
     * 分页查询已上架spu
     *
     * @param categoryId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getSpu/byCategoryId/{id}")
    @ApiIgnore
    public UnifyWithBody<PageResult> getSpuByCategoryId(@Min(value = 1, message = "最小为1的正整数") @PathVariable(name = "id") Integer categoryId,
                                            @RequestParam(name = "current", defaultValue = "1") Long current,
                                            @RequestParam(name = "size", defaultValue = "10") Long size) {
        PageResult pageResult = spuServiceImpl.querySpuByCategoryId(categoryId, new PageParam(current, size));
        return new UnifyWithBody<>(pageResult);
    }

    /**
     * 根据状态获取所有spu
     *
     * @param status
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getSpus/byStatus/{status}")
    @ApiOperation("商品(spu)管理 - 根据上架状态分页查询spu")
    public UnifyWithBody getSpuListByStatus(@Min(value = 0,message = "status越界")@Max(value = 1,message = "status越界")@PathVariable(value = "status") Integer status,
                                                @RequestParam(name = "current", defaultValue = "1") Long current,
                                                @RequestParam(name = "size", defaultValue = "10") Long size) {
        PageResult pageResult = service.getSpus(status, new PageParam(current, size));

        return new UnifyWithBody(pageResult);
    }

    /**
     * 获取一条spu下所有sku
     *
     * @param id
     * @return
     */
    @GetMapping("/getSpuWithSkus/{id}")
    @ApiOperation("商品(spu)管理 - 获取一条spu下所有sku")
    public UnifyWithBody<SpuSimpleVoWithSkus> getSpuWithSkus(@Min(value = 1, message = "id最小为1") @PathVariable(value = "id") Integer id) {
        SpuSimpleVoWithSkus spuSimpleVoWithSkus = service.getSpuWithSkus(id);
        return new UnifyWithBody(spuSimpleVoWithSkus);
    }

    /**
     * 添加一个商品
     *
     * @param spuDto
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("商品(spu)管理 - 添加一条spu数据")
    public UnifyResponse addSpu(@Validated({SaveValidGroup.class})@RequestBody SpuDto spuDto, HttpServletRequest request) {
        return service.add(spuDto, request);
    }

    /**
     * 级联修改一个spu
     *
     * @param spuDto
     * @param request
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("商品(spu)管理 - 修改一条spu数据")
    public UnifyResponse updateSpu(@Validated({UpdateValidGroup.class})@RequestBody SpuDto spuDto, HttpServletRequest request) {
        return service.update(spuDto, request);
    }

    /**
     * 上下架一个spu
     * @param id
     * @param status
     * @param request
     * @return
     */
    @GetMapping("/upOrDown/{id}/{status}")
    @ApiOperation("商品(spu)管理 - 上下架一个spu")
    public UnifyResponse upOrDown(@Min(value = 1,message = "id最小为1")@PathVariable("id") Integer id,
                                  @Min(value = 0,message = "status越界")@Max (value = 1,message = "status越界")@PathVariable("status") Integer status,
                                  HttpServletRequest request) {
        return service.upOrDown(new UpOrDownBo(id,status),request);
    }


    /**
     * 删除一个 没有sku关联的spu
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/delete/by/{id}")
    @ApiOperation("商品(spu)管理 - 删除一个没有sku关联的spu")
    public UnifyResponse deleteSpu(@Min(value = 1,message = "id最小为1")@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        return service.delete(id,request);
    }
}
