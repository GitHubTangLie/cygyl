package com.example.cygyl;

import com.example.cygyl.sys.entity.SysCompanyEntity;
import com.example.cygyl.sys.service.SysCompanyService;
import com.example.cygyl.util.PageUitls;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/26
 */
@SpringBootTest
public class CompanyTest {

    @Autowired
    SysCompanyService companyService;

    @Test
    public void getCompany() {

        Map<String, Object> map = new HashMap<>();
        map.put("create_user_id",1);
//        map.put("company_name", "油");
        PageUitls pageUitls = companyService.selectMerchantsList(map);
        System.out.println(pageUitls);
    }
}
