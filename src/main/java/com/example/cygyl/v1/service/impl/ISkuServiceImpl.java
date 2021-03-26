package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.Compoent.UserTokenService;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.enums.SkuStatusEnum;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SkuDto;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.SkuHistoryEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.pojo.UnitEntity;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.example.cygyl.util.BeanMapper;
import com.example.cygyl.v1.dao.SkuHistoryMapper;
import com.example.cygyl.v1.dao.SkuMapper;
import com.example.cygyl.v1.dao.SpuMapper;
import com.example.cygyl.v1.dao.UnitMapper;
import com.example.cygyl.v1.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/17 13:47
 */

@Service
public class ISkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private SkuHistoryMapper skuHistoryMapper;
    @Autowired
    private UserTokenService userTokenService;

    /**
     * 根据分类查询所有sku
     * @param status
     * @param pageParam
     * @return
     */
    public PageResult queryAllByStatus(int status,PageParam pageParam) {
        return qMaster(status, pageParam);
    }
    /**
     * 添加一个sku
     * @param skuDto
     * @param request
     * @return
     */
    public UnifyResponse add(SkuDto skuDto, HttpServletRequest request) {
        // TODO: 2021/3/17 操作记录
        sMaster(skuDto,request);
        return UnifyResponse.OK(request);
    }

    /**
     * 修改一条sku 记录操作
     *
     * @param skuDto
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public UnifyResponse update(SkuDto skuDto, HttpServletRequest request) {
        // TODO: 2021/3/17 操作记录
        SkuEntity skuEntity = uMaster(skuDto,request);
        uMasterForHistory(skuEntity);
        return UnifyResponse.OK(request);
    }

    /**
     * 上下架
     *
     * @param bo
     * @param request
     * @return
     */
    public UnifyResponse upOrDown(UpOrDownBo bo, HttpServletRequest request) {
        // TODO: 2021/3/17 操作记录
        udMaster(bo);
        return UnifyResponse.OK(request);
    }
    //------------------------------------------------------------------

    /**
     * 根据状态分页查询
     * @param status
     * @param pageParam
     * @return
     */
    private PageResult qMaster(int status, PageParam pageParam) {
        Page<SkuEntity> page = skuMapper.getSkusByStatus(
                new Page(pageParam.getCurrent(), pageParam.getSize()), status);

        return BeanMapper.mapPageForSku(new PageResult<SkuEntity>(page), new PageResult<SkuSimpleVo>());
    }

    /**
     * 上下架
     * @param bo
     */
    private void udMaster(UpOrDownBo bo) {
        isExist(bo.getId());
        int result = skuMapper.updateStatusById(bo.getId(), bo.getStatus());
        isOK(result,10003);
    }

    /**
     * 增加一条sku
     * @param skuDto
     */
    private void sMaster(SkuDto skuDto,HttpServletRequest request) {
        isExist(skuDto.getSpecs());
        isSpuExist(skuDto.getSpuId());
        isUnitExist(skuDto.getUnitId());
        int result = skuMapper.insert(initAdd(skuDto,request));
        isOK(result,10002);
    }


    /**
     * 修改一条sku 并返回其信息
     * @param skuDto
     * @return
     */
    private SkuEntity uMaster(SkuDto skuDto,HttpServletRequest request) {
        isExist(skuDto.getId());
        isSpuExist(skuDto.getSpuId());
        isUnitExist(skuDto.getUnitId());
        SkuEntity skuEntity = skuMapper.selectById(skuDto.getId());
        int result = skuMapper.updateById(initUpdate(skuDto,request));
        isOK(result,10003);
        return skuEntity;
    }

    /**
     * 增加一条sku修改记录
     * @param skuEntity
     */
    private void uMasterForHistory(SkuEntity skuEntity) {
        SkuHistoryEntity skuHistoryEntity = new SkuHistoryEntity(skuEntity);
        int result = skuHistoryMapper.insert(skuHistoryEntity);
        isOK(result,10002);
    }

    private void isExist(String specs) {
        List<SkuEntity> skuEntityList = skuMapper.selectList(
                new QueryWrapper<SkuEntity>().eq("specs", specs));
        if (!skuEntityList.isEmpty()) {
            throw new ForbiddenException(20005);
        }
    }

    protected void isExist(int id) {
        SkuEntity skuEntity = skuMapper.selectById(id);
        if (skuEntity == null) {
            throw new NotFoundException(20006);
        }
    }

    private void isSpuExist(int sid) {
        SpuEntity spuEntity = spuMapper.selectById(sid);
        if (spuEntity == null) {
            throw new NotFoundException(20004);
        }
    }

    private void isUnitExist(int uid) {
        UnitEntity unitEntity = unitMapper.selectById(uid);
        if (unitEntity == null) {
            throw new NotFoundException(20008);
        }
    }

    private SkuEntity initAdd(SkuDto skuDto,HttpServletRequest request) {
        SkuEntity skuEntity = BeanMapper.map(skuDto, SkuEntity.class);
        skuEntity.setUserId(userTokenService.getUserIdByToken(request));
        skuEntity.setCreateTime(new Date());
        skuEntity.setUpdateTime(new Date());
        skuEntity.setStatus(SkuStatusEnum.ON.getCode());
        return skuEntity;
    }

    private SkuEntity initUpdate(SkuDto skuDto,HttpServletRequest request) {
        SkuEntity skuEntity = BeanMapper.map(skuDto, SkuEntity.class);
        skuEntity.setUpdateTime(new Date());
        skuEntity.setUserId(userTokenService.getUserIdByToken(request));
        return skuEntity;
    }

    private void isOK(int result,int code) {
        if (result < 1) {
            throw new ForbiddenException(code);
        }
    }
}
