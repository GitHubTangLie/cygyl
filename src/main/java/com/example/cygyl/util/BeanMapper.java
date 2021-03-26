package com.example.cygyl.util;

import com.example.cygyl.Compoent.PageResult;
import com.example.cygyl.model.pojo.SkuEntity;
import com.example.cygyl.model.vo.SkuSimpleVo;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.MappingException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 黎源
 * @date 2021/3/9 9:49
 */
@Slf4j
public class BeanMapper {
    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * 普通javaBean 映射
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            return mapper.map(source, targetClass);
        } catch (MappingException e) {
            log.error("Dozermapper类转换异常");
            throw new RuntimeException();
        }
    }

    /**
     * list转换 如list<a> -> list<b>
     * @param sourceList
     * @param targetClass
     * @return
     */
    public static <T> List<T> mapList(List sourceList, Class<T> targetClass) {
        List targetList = new ArrayList<T>();
        if (sourceList.isEmpty()) {
            return targetList;
        }
        for (Object o : sourceList) {
            if (o != null) {
                try {
                    targetList.add((T)mapper.map(o,targetClass));
                } catch (MappingException e) {
                    log.error(e.getMessage());
                    log.error(e.getLocalizedMessage());
                    log.error("Dozermapper类转换异常");
                    throw new RuntimeException();
                }
            }
        }
        return targetList;
    }

    /**
     * 如需要重新封装pageResult 的集合调用此方法
     *
     * @param target
     * @return
     */
    public static <T, K> PageResult<K> mapPage(PageResult<T> result,
                                               PageResult<K> newResult,
                                               Class<K> target) {
        List<K> newItems = BeanMapper.mapList(result.getItems(), target);
        newResult.setTotal(result.getTotal());
        newResult.setSize(result.getSize());
        newResult.setCurrent(result.getCurrent());
        newResult.setItems(newItems);
        return newResult;
    }

    public static PageResult<SkuSimpleVo> mapPageForSku(PageResult<SkuEntity> result,
                                               PageResult<SkuSimpleVo> newResult) {
        List<SkuSimpleVo> newItems = result.getItems().stream().map(SkuSimpleVo::new).collect(Collectors.toList());
//        List<SkuSimpleVo> newItems = new ArrayList<>();
//        result.getItems().forEach(item-> System.out.println(item));
        newResult.setTotal(result.getTotal());
        newResult.setSize(result.getSize());
        newResult.setCurrent(result.getCurrent());
        newResult.setItems(newItems);
        return newResult;
    }

}
