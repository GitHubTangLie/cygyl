package com.example.cygyl.v1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.exception.NotFoundException;
import com.example.cygyl.model.pojo.UnitEntity;
import com.example.cygyl.v1.dao.UnitMapper;
import com.example.cygyl.v1.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/12 16:12
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, UnitEntity> implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    /**
     * 查询所有单位
     * @return
     */
    public List<UnitEntity> selectAll() {
        List<UnitEntity> units = super.list();
        if (units.isEmpty()) {
            throw new NotFoundException(10001);
        }
        return units;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public UnitEntity selectById(Integer id) {
        UnitEntity unitEntity = super.getById(id);
        if (unitEntity == null) {
            throw new NotFoundException(10001);
        }
        return unitEntity;
    }

    /**
     * 插入一条数据
     * @param unitEntity
     * @param request
     * @return
     */
    public UnifyResponse add(UnitEntity unitEntity,HttpServletRequest request) {
        Boolean isExist = isExist(unitEntity.getName());
        if (isExist) {
            throw new ForbiddenException(20007);
        }
        int result = unitMapper.insert(unitEntity);
        if (result < 1) {
            throw new ForbiddenException(10002);
        }
        return UnifyResponse.OK(request);
    }

    /**
     * 修改一个单位
     * @param unitEntity
     * @param request
     * @return
     */
    public UnifyResponse update(UnitEntity unitEntity, HttpServletRequest request) {
        Boolean isEsist = isExist(unitEntity.getId());
        if (!isEsist) {
            throw new NotFoundException(20008);
        }
        int result = unitMapper.updateById(unitEntity);
        if (result < 1) {
            throw new ForbiddenException(10003);
        }
        return UnifyResponse.OK(request);
    }
    /**
     * 根据id 删除一个单位
     * @param id
     * @param request
     * @return
     */
    public UnifyResponse deleteById(Integer id, HttpServletRequest request) {
        int result = unitMapper.deleteById(id);
        if (result < 1) {
            throw new ForbiddenException(10004);
        }
        return UnifyResponse.OK(request);
    }

    /**
     * 判断单位是否存在
     * @param name
     * @return
     */
    private Boolean isExist(String name) {
        UnitEntity unitEntity = unitMapper.selectOne(new QueryWrapper<UnitEntity>()
                .eq("name", name));
        return unitEntity != null;
    }

    private Boolean isExist(Integer id) {
        UnitEntity unitEntity = unitMapper.selectById(id);
        return unitEntity != null;
    }
}
