package com.example.cygyl.v1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.model.dto.CategoryDto;
import com.example.cygyl.model.pojo.CategoryEntity;
import com.example.cygyl.model.vo.CategoryListVo;
import com.example.cygyl.model.vo.CategoryWithSpusVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/3/8 13:49
 */
public interface CategoryService extends IService<CategoryEntity> {

    UnifyResponse save(CategoryDto categoryDto, HttpServletRequest request);

    UnifyResponse update(CategoryDto categoryDto, HttpServletRequest request);

    UnifyResponse upOrDown(int status, int id, HttpServletRequest request);

    List<CategoryListVo> getCategorysByStatus(int status);

    CategoryWithSpusVo getCategoryWithSpuById(int id);
}
