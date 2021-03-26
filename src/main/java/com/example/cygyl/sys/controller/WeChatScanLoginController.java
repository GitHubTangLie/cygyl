package com.example.cygyl.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:  微信扫码登录
 * @author: Tanglie
 * @time: 2021/3/15
 */
@RestController("/sys")
public class WeChatScanLoginController {

    @GetMapping("/weChatLogin")
    public Map<String,Object> weChatBack(HttpServletRequest request){
        System.out.println("进入到微信回调");
        Map<String,Object> map = new HashMap<>();

        return map;
    }

}
