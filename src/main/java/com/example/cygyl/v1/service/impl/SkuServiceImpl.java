package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SkuDto;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.example.cygyl.util.BeanMapper;
import com.example.cygyl.v1.dao.SkuMapper;
import com.example.cygyl.v1.dao.SpuMapper;
import com.example.cygyl.v1.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/3/11 9:18
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;
    /**
     * 通过spuid 查找其 sku  是否上架可自定义
     * @param sid
     * @param status
     * @return
     */
    public List<SkuSimpleVo> getSkuBySpuId(Integer sid, Integer status) {
        List<SkuEntity> skuEntityList=skuMapper.getSkusBySpuId(sid, status);
        if (skuEntityList.isEmpty()) {
            throw new NotFoundException(10001);
        }

        return skuEntityList.stream().map(SkuSimpleVo::new).collect(Collectors.toList());
    }

    /**
     * 添加一个sku
     * @param skuDto
     * @param request
     * @return
     */
    public UnifyResponse add(SkuDto skuDto, HttpServletRequest request) {
        Boolean isExist = skuIsExist(skuDto);
        if (isExist) {
            throw new NotFoundException(20005);
        }
        isExist = spuIsExist(skuDto);
        if (!isExist) {
            throw new NotFoundException(20004);
        }
        SkuEntity skuEntity = initForAdd(skuDto);
        int result = skuMapper.insert(skuEntity);
        if (result < 1) {
            throw new ForbiddenException(10002);
        }
        return UnifyResponse.OK(request);
    }

    /**
     * 修改sku
     * @param skuDto
     * @param request
     * @return
     */
    public UnifyResponse update(SkuDto skuDto, HttpServletRequest request) {
        Boolean isExist = skuIsExistById(skuDto);
        if (!isExist) {
            throw new ForbiddenException(20006);
        }
        SkuEntity skuEntity = initForUpdate(skuDto);
        int result = skuMapper.updateById(skuEntity);
        if (result < 1) {
            throw new ForbiddenException(10003);
        }
        return UnifyResponse.OK(request);
    }

    @Override
    public UnifyResponse upOrDown(UpOrDownBo bo, HttpServletRequest request) {
        return null;
    }

    @Override
    public PageResult queryAllByStatus(int status, PageParam pageParam) {
        return null;
    }

    /**
     * 删除一个sku
     * @param id
     * @param request
     * @return
     */
    public UnifyResponse deleteSku(Integer id,HttpServletRequest request) {
        Boolean isExist = skuIsExistById(id);
        if (!isExist) {
            throw new ForbiddenException(20006);
        }

        int result = skuMapper.deleteById(id);
        if (result < 1) {
            throw new ForbiddenException(10004);
        }
        return UnifyResponse.OK(request);
    }

    /**
     * 判断 sku spu是否存在
     * @param skuDto
     * @return
     */
    private Boolean skuIsExist(SkuDto skuDto) {
        SkuEntity skuEntity = skuMapper.selectOne(new QueryWrapper<SkuEntity>()
                .eq("specs", skuDto.getSpecs()));
        return skuEntity != null;
    }
    private Boolean skuIsExistById(SkuDto skuDto) {
        SkuEntity skuEntity = skuMapper.selectById(skuDto.getId());
        return skuEntity != null;
    }
    private Boolean skuIsExistById(Integer id) {
        SkuEntity skuEntity = skuMapper.selectById(id);
        return skuEntity != null;
    }
    private Boolean spuIsExist(SkuDto skuDto) {
        SpuEntity spuEntity = spuMapper.selectById(skuDto.getSpuId());
        return spuEntity != null;
    }



    /**
     * 初始化 入参
     * @param skuDto
     * @return
     */
    private SkuEntity initForAdd(SkuDto skuDto) {
        SkuEntity skuEntity = BeanMapper.map(skuDto, SkuEntity.class);
        // TODO: 2021/3/12 用户
        skuEntity.setCreateTime(new Date());
        skuEntity.setUpdateTime(new Date());
        skuEntity.setStatus(skuDto.getStatus());
        return skuEntity;
    }

    private SkuEntity initForUpdate(SkuDto skuDto) {
        SkuEntity skuEntity = BeanMapper.map(skuDto, SkuEntity.class);
        // TODO: 2021/3/12 用户
        skuEntity.setUpdateTime(new Date());
        skuEntity.setStatus(skuDto.getStatus());
        return skuEntity;
    }
}
