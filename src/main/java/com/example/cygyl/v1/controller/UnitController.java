package com.example.cygyl.v1.controller;

import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.core.response.UnifyWithBody;
import com.example.cygyl.core.response.UnifyWithBodyList;
import com.example.cygyl.model.pojo.UnitEntity;
import com.example.cygyl.v1.service.UnitService;
import com.example.cygyl.v1.service.impl.UnitServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/12 16:23
 */
@RestController
@RequestMapping("/unit")
@Api(tags = "单位管理")
public class UnitController {

    @Autowired
    private UnitServiceImpl unitServiceImpl;

    @GetMapping("/queryAll")
    @ApiOperation("单位管理 - 获取所有单位")
    public UnifyWithBodyList queryAll() {
        return new UnifyWithBodyList(unitServiceImpl.list());
    }

    @GetMapping("/query/by/{id}")
    @ApiOperation("单位管理 - 根据id获取一个单位")
    public UnifyWithBody<UnitEntity> queryAll(@PathVariable(name = "id")Integer id) {
        return new UnifyWithBody<>(unitServiceImpl.selectById(id));
    }

    @GetMapping("/delete/by/{id}")
    @ApiOperation("单位管理 - 根据id删除一个单位")
    public UnifyResponse deleteById(@PathVariable(name = "id")Integer id, HttpServletRequest request) {
        return unitServiceImpl.deleteById(id,request);
    }

    @PostMapping("/add")
    @ApiOperation("单位管理 - 新增一个单位")
    public UnifyResponse add(@RequestBody UnitEntity unitEntity,HttpServletRequest request) {
        return unitServiceImpl.add(unitEntity, request);
    }

    @PostMapping("/update")
    @ApiOperation("单位管理 - 修改一个单位")
    public UnifyResponse update(@RequestBody UnitEntity unitEntity, HttpServletRequest request) {
        return unitServiceImpl.update(unitEntity,request);
    }
}
