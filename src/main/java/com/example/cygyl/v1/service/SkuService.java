package com.example.cygyl.v1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SkuDto;
import com.example.cygyl.model.pojo.SkuEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2021/3/11 9:18
 */
public interface SkuService extends IService<SkuEntity> {
    UnifyResponse add(SkuDto skuDto, HttpServletRequest request);

    UnifyResponse update(SkuDto skuDto, HttpServletRequest request);

    UnifyResponse upOrDown(UpOrDownBo bo, HttpServletRequest request);

    PageResult queryAllByStatus(int status, PageParam pageParam);
}
