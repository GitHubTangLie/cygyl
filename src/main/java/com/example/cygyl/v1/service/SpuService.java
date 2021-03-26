package com.example.cygyl.v1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SpuDto;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.SpuSimpleVoWithSkus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2021/3/9 11:39
 */
public interface SpuService extends IService<SpuEntity> {
    UnifyResponse add(SpuDto spuDto, HttpServletRequest request);

    UnifyResponse update(SpuDto spuDto, HttpServletRequest request);

    UnifyResponse upOrDown(UpOrDownBo bo, HttpServletRequest request);

    UnifyResponse delete(int id, HttpServletRequest request);

    SpuSimpleVoWithSkus getSpuWithSkus(Integer id);

    PageResult getSpus(int status, PageParam pageParam);
}
