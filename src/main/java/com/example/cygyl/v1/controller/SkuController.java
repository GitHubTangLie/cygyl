package com.example.cygyl.v1.controller;

import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.core.response.UnifyWithBody;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SkuDto;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVo;
import com.example.cygyl.util.valid.SaveValidGroup;
import com.example.cygyl.util.valid.UpdateValidGroup;
import com.example.cygyl.v1.service.SkuService;
import com.example.cygyl.v1.service.impl.SkuServiceImpl;
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
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/11 9:37
 */
@RestController
@RequestMapping("/sku")
@Validated
@Api(tags = "单品管理(sku)")
public class SkuController {

    @Autowired
    private SkuServiceImpl skuServiceImpl;
    @Autowired
    @Qualifier(value = "ISkuServiceImpl")
    private SkuService service;


    @GetMapping("/getSkusBySpuId")
    @ApiIgnore
    public List<SkuSimpleVo> getSkusBySpuId(@Min (value = 1,message = "请传入正确的spuId")@RequestParam(value = "spuId") Integer id,
                                            @RequestParam(value = "isOn",defaultValue = "true") Boolean isOn) {
        return skuServiceImpl.getSkuBySpuId(id,isOn?1:0);
    }

    /**
     * 添加一个 sku
     * @param skuDto
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("单品(sku)管理 - 添加一个sku")
    public UnifyResponse addSku(@Validated({SaveValidGroup.class}) @RequestBody SkuDto skuDto, @ApiIgnore HttpServletRequest request) {
        return service.add(skuDto, request);
    }

    /**
     * 修改一个sku
     * @param skuDto
     * @param request
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("单品(sku)管理 - 修改一个sku")
    public UnifyResponse updateSku(@Validated({UpdateValidGroup.class})@RequestBody SkuDto skuDto, HttpServletRequest request) {
        return service.update(skuDto, request);
    }

    /**
     * 删除一个sku
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete/by/{id}")
    @ApiOperation("单品(sku)管理 - 删除一个sku")
    public UnifyResponse deleteSku(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        return skuServiceImpl.deleteSku(id, request);
    }

    /**
     * sku上下架
     * @param id
     * @param status
     * @param request
     * @return
     */
    @GetMapping("/upOrDown/{id}/{status}")
    @ApiOperation("单品(sku)管理 - 上下架一个sku")
    public UnifyResponse upOrDown(@Min(value = 1,message = "id最小为1")@PathVariable("id") Integer id,
                                  @Min(value = 0,message = "status越界")@Max(value = 1,message = "status越界")@PathVariable("status") Integer status,
                                  HttpServletRequest request) {
        return service.upOrDown(new UpOrDownBo(id,status),request);
    }

    @GetMapping("/getSkus/byStatus/{status}")
    @ApiOperation("单品(sku)管理 - 根据上下架状态分页列表查询")
    public UnifyWithBody<PageResult> getSpuListByStatus(@Min(value = 0,message = "status越界")@Max(value = 1,message = "status越界")@PathVariable(value = "status") Integer status,
                                            @RequestParam(name = "current", defaultValue = "1") Long current,
                                            @RequestParam(name = "size", defaultValue = "10") Long size) {
        PageResult pageResult = service.queryAllByStatus(status, new PageParam(current, size));
        return new UnifyWithBody<>(pageResult);
    }
}
