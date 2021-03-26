package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.enums.SkuStatusEnum;
import com.example.cygyl.enums.SpuStatusEnum;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SpuDto;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.SpuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVoWithSkus;
import com.example.cygyl.util.BeanMapper;
import com.example.cygyl.v1.dao.SkuMapper;
import com.example.cygyl.v1.dao.SpuMapper;
import com.example.cygyl.v1.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/9 11:40
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SkuMapper skuMapper;

    /**
     * 根据分类id查询已上架 spu
     * @param cid
     * @param pageParam
     * @return
     */
    public PageResult<SpuSimpleVo> querySpuByCategoryId(Integer cid, PageParam pageParam) {
        IPage<SpuEntity> page = spuMapper.selectPage(
                new Page<>(pageParam.getCurrent(), pageParam.getSize()),
                new QueryWrapper<SpuEntity>()
                .eq("status", SpuStatusEnum.ON.getCode())
                .eq("category_id", cid));
        if (page.getRecords().isEmpty()) {
            throw new NotFoundException(10001);
        }
        return BeanMapper.mapPage(new PageResult<>(page), new PageResult<SpuSimpleVo>(), SpuSimpleVo.class);
    }

    /**
     * 根据状态查询所有spu
     * @param status
     * @param pageParam
     * @return
     */
    public PageResult<SpuSimpleVo> querySpuList(Integer status,PageParam pageParam) {
        IPage<SpuEntity> page = spuMapper.selectPage(
                new Page<>(pageParam.getCurrent(), pageParam.getSize()),
                new QueryWrapper<SpuEntity>().eq("status", status));
        if (page.getRecords().isEmpty()) {
            throw new NotFoundException(10001);
        }
        return BeanMapper.mapPage(new PageResult<>(page), new PageResult<SpuSimpleVo>(), SpuSimpleVo.class);

    }

    /**
     * 添加一个spu
     * @param spuDto
     * @param request
     * @return
     */
    public UnifyResponse add(SpuDto spuDto, HttpServletRequest request) {
        // TODO: 2021/3/11 根据token获取用户信息
        Boolean isExist = spuIsExistByTitle(spuDto.getTitle());
        if (isExist) {
            throw new ForbiddenException(20003);
        }
        SpuEntity spuEntity = initDataForAdd(spuDto);
        int result = spuMapper.insert(spuEntity);
        if (result < 1) {
            throw new ForbiddenException(10002);
        }
        return UnifyResponse.OK(request);
    }

    /**
     * 级联修改spu sku
     * @param spuDto
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public UnifyResponse update(SpuDto spuDto,HttpServletRequest request) {
        //todo 根据token获取用户信息
        //组装 spuDto
        SpuEntity spuEntity = initDataForUpdate(spuDto);
        //查询该spu是否存在
        Boolean isExist = spuIsExistById(spuDto.getId());
        if (!isExist) {
            throw new ForbiddenException(20004);
        }
        //修改spu信息
        int result = spuMapper.updateById(spuEntity);
        if (result < 1){
            throw new ForbiddenException(10003);
        }
        // 级联修改sku的status categoryId
        SkuEntity skuEntity = SkuEntity.builder()
//                .status(SkuStatusEnum.toType(spuEntity.getStatus()))
                .categoryId(spuEntity.getCategoryId())
                .build();
        skuEntity.setUpdateTime(new Date());
        skuMapper.update(skuEntity, new QueryWrapper<SkuEntity>()
                .eq("spu_id", spuEntity.getId()));
        return UnifyResponse.OK(request);
    }

    @Override
    public UnifyResponse upOrDown(UpOrDownBo bo, HttpServletRequest request) {
        return null;
    }

    @Override
    public UnifyResponse delete(int id, HttpServletRequest request) {
        return null;
    }

    @Override
    public SpuSimpleVoWithSkus getSpuWithSkus(Integer id) {
        return null;
    }

    @Override
    public PageResult getSpus(int status, PageParam pageParam) {
        return null;
    }

    /**
     * 级联删除
     * @param id
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public UnifyResponse delete(Integer id,HttpServletRequest request) {
        //  级联删除
        Boolean isExist = spuIsExistById(id);
        if (!isExist) {
            throw new NotFoundException(20004);
        }
        int result = spuMapper.deleteById(id);
        if (result < 1) {
            throw new ForbiddenException(10004);
        }
        skuMapper.delete(new QueryWrapper<SkuEntity>().eq("spu_id", id));
        return UnifyResponse.OK(id, request);
    }

    /**
     * spu是否存在
     * @param title
     * @return
     */
    private Boolean spuIsExistByTitle(String title) {
        SpuEntity spuEntity = spuMapper.selectOne(new QueryWrapper<SpuEntity>()
                .eq("title",title));
        return spuEntity != null;
    }

    private Boolean spuIsExistById(Integer id) {
        SpuEntity spuEntity = spuMapper.selectById(id);
        return spuEntity != null;
    }

    /**
     * 封装查询对象
     * @param spuDto
     * @return
     */
    private SpuEntity initDataForAdd(SpuDto spuDto) {
        SpuEntity spuEntity = BeanMapper.map(spuDto, SpuEntity.class);
        // TODO: 2021/3/11 添加用户信息
        spuEntity.setCreateTime(new Date());
        spuEntity.setUpdateTime(new Date());
        return spuEntity;
    }

    private SpuEntity initDataForUpdate(SpuDto spuDto) {
        SpuEntity spuEntity = BeanMapper.map(spuDto, SpuEntity.class);
        // TODO: 2021/3/11 添加用户信息
        spuEntity.setUpdateTime(new Date());
        return spuEntity;
    }
}
