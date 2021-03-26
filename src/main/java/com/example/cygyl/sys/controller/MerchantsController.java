package com.example.cygyl.sys.controller;

import com.example.cygyl.sys.service.SysCompanyService;
import com.example.cygyl.util.Constant;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 商家管理
 * @author: Tanglie
 * @time: 2021/3/26
 */
@RestController
@RequestMapping("/sys/merchant")
public class MerchantsController extends AbstractController{

    @Autowired
    private SysCompanyService companyService;

    /**
     *  获取自身创建的商家列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:merchants:list")
    public R list(@RequestParam Map<String,Object> parms){
//        Map<String,Object> map = new HashMap<>();
        if (getUserId() != Constant.SUPER_ADMIN){
            parms.put("update_user_id",getUserId());
        }
        PageUitls pageUitls = companyService.selectMerchantsList(parms);

        return R.ok().put("page",pageUitls);
    }
}
