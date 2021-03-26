package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.enums.CategoryStatusEnum;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.dto.CategoryDto;
import com.example.cygyl.model.pojo.CategoryEntity;
import com.example.cygyl.model.pojo.SpuEntity;
import com.example.cygyl.model.vo.CategoryListVo;
import com.example.cygyl.model.vo.CategoryWithSpusVo;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.example.cygyl.model.vo.SpuSimpleVo;
import com.example.cygyl.util.BeanMapper;
import com.example.cygyl.v1.dao.CategoryMapper;
import com.example.cygyl.v1.dao.SkuMapper;
import com.example.cygyl.v1.dao.SpuMapper;
import com.example.cygyl.v1.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/15 16:37
 */
@Service
public class ICategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SpuMapper spuMapper;

    /**
     * 根据上架状态查询所有分类
     * @param status
     * @return
     */
    public List<CategoryListVo> getCategorysByStatus(int status) {
        List<CategoryEntity> categoryEntities = getCategorys(status);
        return BeanMapper.mapList(categoryEntities, CategoryListVo.class);
    }

    /**
     * 查询某一个分类下所有spu
     * @param id
     * @return
     */
    public CategoryWithSpusVo getCategoryWithSpuById(int id) {
        //  2021/3/16 查询category
        CategoryEntity entity = categoryMapper.selectById(id);
        if (entity == null) {
            throw new NotFoundException(20002);
        }

        // 2021/3/16 查询次分类所有spu
        List<SpuSimpleVo> spuSimpleVos = querySpuByCid(entity.getId());
        // 2021/3/16 组合数据
        return new CategoryWithSpusVo(entity, spuSimpleVos);
    }

    /**
     * 新增
     * @param categoryDto
     * @param request
     * @return
     */
    @Override
    public UnifyResponse save(CategoryDto categoryDto, HttpServletRequest request) {
        sMaster(initAdd(categoryDto));
        return UnifyResponse.OK(request);
    }

    /**
     * 修改 无级联 修改分类不应该修改商品 否则可能会出现 苹果-->肉类
     * @param categoryDto
     * @param request
     * @return
     */
    public UnifyResponse update(CategoryDto categoryDto, HttpServletRequest request) {
        uMaster(initUpdate(categoryDto));
        return UnifyResponse.OK(request);
    }

    /**
     * 上下架
     * @param status
     * @param id
     * @return
     */
    public UnifyResponse upOrDown(int id, int status,HttpServletRequest request) {
        isExist(id);
        int result = categoryMapper.updateStatusById(id, status);
        isOk(result,10003);
        return UnifyResponse.OK(request);
    }

    /**
     * 查询某分类下所有上架spu
     * @param cid
     * @return
     */
    private List<SpuSimpleVo> querySpuByCid(int cid) {
        List<SpuEntity> entityList = spuMapper.selectList(new QueryWrapper<SpuEntity>()
                .eq("category_id", cid).eq("status",1));
        if (entityList.isEmpty()) {
            throw new NotFoundException(20004);
        }
        return BeanMapper.mapList(entityList, SpuSimpleVo.class);
    }

    /**
     * 查询所有分类
     * @param status
     * @return
     */
    private List<CategoryEntity> getCategorys(int status) {
        List<CategoryEntity> categoryList = categoryMapper
                .selectList(new QueryWrapper<CategoryEntity>()
                        .eq("status",status)
                        .orderByAsc("level"));
        if (categoryList.isEmpty()) {
            throw new NotFoundException(10001);
        }
        return categoryList;
    }

    /**
     * 新增category
     * @param entity
     * @return
     */
    private CategoryEntity sMaster(CategoryEntity entity) {
        isExist(entity.getName());
        int result = categoryMapper.insert(entity);
        isOk(result,10002);
        return entity;
    }

    /**
     * 修改category
     * @param entity
     * @return
     */
    private CategoryEntity uMaster(CategoryEntity entity) {
        isExist(entity.getId());
        int result = categoryMapper.updateById(entity);
        isOk(result, 10003);
        return entity;
    }

    /**
     * 判断新增的category 是否存在
     * @param cName
     */
    private void isExist(String cName) {
        CategoryEntity entity = categoryMapper.selectOne(new QueryWrapper<CategoryEntity>()
                .eq("name", cName));
        if (entity != null) {
            throw new ForbiddenException(20001);
        }
    }

    private void isExist(int id) {
        CategoryEntity entity = categoryMapper.selectById(id);
        if (entity == null) {
            throw new ForbiddenException(20002);
        }
    }

    /**
     * save update delete 操作是否成功
     * @param result
     * @param code
     */
    private void isOk(int result,int code) {
        if (result < 1) {
            throw new ForbiddenException(code);
        }
    }

    /**
     * 初始化 新增数据
     * @param dto
     * @return
     */
    private CategoryEntity initAdd(CategoryDto dto) {
        CategoryEntity entity = BeanMapper.map(dto, CategoryEntity.class);
        entity.setStatus(CategoryStatusEnum.ON.getCode());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        return entity;
    }

    /**
     * 初始化修改数据
     * @param dto
     * @return
     */
    private CategoryEntity initUpdate(CategoryDto dto) {
        CategoryEntity entity = BeanMapper.map(dto, CategoryEntity.class);
        entity.setUpdateTime(new Date());
        return entity;
    }
}
