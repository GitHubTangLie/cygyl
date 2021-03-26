package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.Compoent.PageParam;
import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.Compoent.UserTokenService;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.enums.SkuStatusEnum;
import com.example.cygyl.enums.SpuStatusEnum;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.bo.UpOrDownBo;
import com.example.cygyl.model.dto.SpuDto;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVoWithSkus;
import com.example.cygyl.util.BeanMapper;
import com.example.cygyl.v1.dao.SkuMapper;
import com.example.cygyl.v1.dao.SpuMapper;
import com.example.cygyl.v1.service.SkuService;
import com.example.cygyl.v1.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/3/16 13:53
 */
@Service
public class ISpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private UserTokenService userTokenService;
    /**
     * 添加一条spu
     * @param spuDto
     * @param request
     * @return
     */
    @Override
    public UnifyResponse add(SpuDto spuDto, HttpServletRequest request) {
        // TODO: 2021/3/16 操作员 记录操作
        aMaster(initAdd(spuDto,request));
        return UnifyResponse.OK(request);
    }

    /**
     * 级联修改一条spu
     * @param spuDto
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public UnifyResponse update(SpuDto spuDto, HttpServletRequest request) {
        // TODO: 2021/3/16 操作员 记录操作
        SpuEntity spuEntity = uMaster(initUpdate(spuDto,request));
        uFollower(spuEntity);
        return UnifyResponse.OK(request);
    }

    /**
     * 上下架
     * @param bo
     */
    public UnifyResponse upOrDown(UpOrDownBo bo,HttpServletRequest request) {
        // TODO: 2021/3/17 记录操作员
        udMaster(bo);
        return UnifyResponse.OK(request);
    }

    /**
     * 删除
     * @param id
     * @param request
     * @return
     */
    public UnifyResponse delete(int id,HttpServletRequest request) {
        dMaster(id);
        return UnifyResponse.OK(request);
    }


    /**
     * 查询单挑spu及其skus信息
     * @param id
     * @return
     */
    public SpuSimpleVoWithSkus getSpuWithSkus(Integer id) {
        return qMasterOne(id);
    }

    /**
     * 查询分页信息
     * @param status
     * @param pageParam
     * @return
     */
    public PageResult getSpus(int status, PageParam pageParam) {
        return getSpusMaster(status, pageParam);
    }

    //-------------------------------------------------------------------------------

    /**
     * 根据所有上架或下架spu分页信息
     * @param status
     * @param pageParam
     * @return
     */
    private PageResult getSpusMaster(int status, PageParam pageParam) {
        Page<SpuEntity> page = spuMapper.selectPage(
                new Page<SpuEntity>(pageParam.getCurrent(), pageParam.getSize()),
                new QueryWrapper<SpuEntity>().eq("status", status));
        return BeanMapper.mapPage(new PageResult<>(page), new PageResult<SpuSimpleVo>(), SpuSimpleVo.class);
    }

    /**
     * 查询单挑spu及其skus信息
     * @param id
     * @return
     */
    private SpuSimpleVoWithSkus qMasterOne(int id) {
        SpuSimpleVo spuSimpleVo = uFollowerOneSpu(id);
        List<SkuSimpleVo> skuSimpleVos = uFollowerOneSkus(spuSimpleVo.getId());
        SpuSimpleVoWithSkus spuSimpleVoWithSkus = BeanMapper.map(spuSimpleVo, SpuSimpleVoWithSkus.class);
        spuSimpleVoWithSkus.setSkuSimpleVos(skuSimpleVos);
        return spuSimpleVoWithSkus;
    }

    /**
     * 查询单挑spu
     * @param id
     * @return
     */
    private SpuSimpleVo uFollowerOneSpu(int id) {
        SpuEntity spuEntity = spuMapper.selectById(id);
        if (spuEntity == null) {
            throw new NotFoundException(20004);
        }
        return BeanMapper.map(spuEntity,SpuSimpleVo.class);
    }

    /**
     * 查询一条spu下所有上架的sku
     * @param pid
     * @return
     */
    private List<SkuSimpleVo> uFollowerOneSkus(int pid) {
        List<SkuEntity> skuEntitys = skuMapper.getSkusBySpuId(pid, SkuStatusEnum.ON.getCode());
        List<SkuSimpleVo> skuSimpleVos = skuEntitys.stream().map(SkuSimpleVo::new).collect(Collectors.toList());
        return skuSimpleVos;
    }


    /**
     * 删除操作
     * @param id
     */
    private void dMaster(int id) {
        isExist(id);
        isHasSku(id);
        int result = spuMapper.deleteById(id);
        isOk(result,10004);
    }

    /**
     * 上下架操作，不做级联
     * @param bo
     */
    private void udMaster(UpOrDownBo bo) {
        isExist(bo.getId());
        int result = spuMapper.updateStatusById(bo.getId(), bo.getStatus());
        isOk(result,10003);
    }

    /**
     * 添加
     * @param spuEntity
     * @return
     */
    private SpuEntity aMaster(SpuEntity spuEntity) {
        isExist(spuEntity.getTitle());
        int result = spuMapper.insert(spuEntity);
        isOk(result,10002);
        return spuEntity;
    }

    /**
     * 修改
     * @param spuEntity
     */
    private SpuEntity uMaster(SpuEntity spuEntity) {
        isExist(spuEntity.getId());
        //是否必须设定默认规格
//        if (spuEntity.getDefaultSkuId() != null && spuEntity.getDefaultSkuId() > 0) {
//            isExistDefaultSku(spuEntity.getId(),spuEntity.getDefaultSkuId());
//        }
        int result = spuMapper.updateById(spuEntity);
        isOk(result,10003);
        return spuEntity;
    }

    /**
     * 级联修改
     * @param spuEntity
     */
    private void uFollower(SpuEntity spuEntity) {
        skuMapper.updateForSpu(spuEntity.getId(), spuEntity.getCategoryId());
    }

    /**
     * spu 是否存在
     * @param title
     */
    private void isExist(String title) {
        SpuEntity entity = spuMapper.selectOne(new QueryWrapper<SpuEntity>().eq("title", title));
        if (entity != null) {
            throw new ForbiddenException(20003);
        }
    }
    private void isExist(int id) {
        SpuEntity entity = spuMapper.selectById(id);
        if (entity == null) {
            throw new NotFoundException(20004);
        }
    }

    private void isExistDefaultSku(int pid, int kid) {
        SkuEntity skuEntity = skuMapper.selectOne(new QueryWrapper<SkuEntity>()
                .eq("id", kid).eq("spu_id", pid));
        if (skuEntity == null) {
            throw new NotFoundException(20006);
        }
    }

    /**
     * 操作是否成功
     * @param result
     * @param code
     */
    private void isOk(int result, int code) {
        if (result < 1) {
            throw new ForbiddenException(code);
        }
    }

    /**
     * 初始化新增信息
     * @param spuDto
     * @return
     */
    private SpuEntity initAdd(SpuDto spuDto,HttpServletRequest request) {
        SpuEntity spuEntity = BeanMapper.map(spuDto, SpuEntity.class);
        spuEntity.setUserId(userTokenService.getUserIdByToken(request));
        spuEntity.setCreateTime(new Date());
        spuEntity.setUpdateTime(new Date());
        spuEntity.setStatus(SpuStatusEnum.ON.getCode());
        return spuEntity;
    }

    /**
     * 初始化修改信息
     * @param spuDto
     * @return
     */
    private SpuEntity initUpdate(SpuDto spuDto,HttpServletRequest request) {
        isHasSku(spuDto);
        SpuEntity spuEntity = BeanMapper.map(spuDto, SpuEntity.class);
        spuEntity.setUserId(userTokenService.getUserIdByToken(request));
        spuEntity.setUpdateTime(new Date());
        return spuEntity;
    }


    /**
     * 修改的spu 是否含有sku
     * @param spuDto
     */
    private void isHasSku(SpuDto spuDto) {
        /**
         * 如果用户修改的信息里有title唯一标识，需要确认该spu是否被sku关联，如果有sku关联则不应修改。
         */

        if (spuDto.getTitle() != null) {
            if ("".equals(spuDto.getTitle().trim())) {
                throw new ForbiddenException(30002);
            }
            List<SkuEntity> skuEntityList = skuMapper.selectList(new QueryWrapper<SkuEntity>()
                    .eq("spu_id", spuDto.getId()));
            if (!skuEntityList.isEmpty()) {
                throw new ForbiddenException(30001);
            }
        }
    }

    private void isHasSku(int id) {
        /**
         * 如果用户修改的信息里有title唯一标识，需要确认该spu是否被sku关联，如果有sku关联则不应删除。
         */
        List<SkuEntity> skuEntityList = skuMapper.selectList(new QueryWrapper<SkuEntity>()
                .eq("spu_id", id));
        if (!skuEntityList.isEmpty()) {
            throw new ForbiddenException(30001);
        }
    }
}
