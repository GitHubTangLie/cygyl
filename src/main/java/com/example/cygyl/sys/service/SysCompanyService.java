package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysCompanyEntity;
import com.example.cygyl.util.PageUitls;

import java.util.Map;

/**
 * @description: 商家管理Service
 * @author: Tanglie
 * @time: 2021/3/26
 */
public interface SysCompanyService extends IService<SysCompanyEntity> {


    /**
     *  查询自身创建的所有商家
     * @param map
     * @return
     */
    PageUitls selectMerchantsList(Map<String, Object> parms);
}
