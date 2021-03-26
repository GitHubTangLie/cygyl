package com.example.cygyl;

import com.example.cygyl.sys.entity.SysMenuEntity;
import com.example.cygyl.sys.repository.SysMenuMapper;
import com.example.cygyl.sys.service.SysMenuService;
import com.example.cygyl.sys.service.SysUserTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;


@SpringBootTest
class CygylApplicationTests {


    @Autowired
    SysUserTokenService sysUserTokenService;
    @Autowired
    SysMenuService menuService;
    @Autowired
    SysMenuMapper menuMapper;




}
