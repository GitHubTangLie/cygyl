package com.example.cygyl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.sys.entity.SysCompanyEntity;
import com.example.cygyl.sys.repository.SysCompanyMapper;
import com.example.cygyl.sys.service.SysCompanyService;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 商家管理实现类
 * @author: Tanglie
 * @time: 2021/3/26
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompanyEntity> implements SysCompanyService {

    /**查询所有商家*/
    @Override
    public PageUitls selectMerchantsList(Map<String, Object> parms) {

        Integer create_user_id = (Integer) parms.get("create_user_id");
        String company_name = (String) parms.get("company_name");
        IPage<SysCompanyEntity> page = this.page(
                new Query<SysCompanyEntity>().getPage(parms),
                new QueryWrapper<SysCompanyEntity>()
                .like(StringUtils.isNotBlank(company_name),"company_name",company_name)
                        .eq(create_user_id != null,"create_user_id",create_user_id));
        return new PageUitls(page);
    }
}
