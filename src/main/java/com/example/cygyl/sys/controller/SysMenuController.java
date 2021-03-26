package com.example.cygyl.sys.controller;

import com.example.cygyl.sys.entity.SysMenuEntity;
import com.example.cygyl.sys.service.ShiroService;
import com.example.cygyl.sys.service.SysMenuService;
import com.example.cygyl.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @description: 系统菜单管理
 * @author: Tanglie
 * @time: 2021/3/16
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航栏
     *
     * @return
     */
    @RequestMapping("/nav")
//    @RequiresPermissions("sys:user:nav")
    public R nav() {
        //查询
        List<SysMenuEntity> menuList = menuService.getUserMenuList(getUserId());
        Set<String> permission = shiroService.selectUserPermission(getUserId());

        return R.ok().put("menuList", menuList).put("permission", permission);

    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = menuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = menuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        return menuList;
    }
}
