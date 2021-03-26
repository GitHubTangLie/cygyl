package com.example.cygyl.sys.controller;

import com.example.cygyl.sys.entity.SysRoleEntity;
import com.example.cygyl.sys.service.SysRoleMenuService;
import com.example.cygyl.sys.service.SysRoleService;
import com.example.cygyl.util.Constant;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 角色管理
 * @author: Tanglie
 * @time: 2021/3/22
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController{

    @Autowired
    SysRoleService roleService;

    @Autowired
    SysRoleMenuService roleMenuService;

    /**
     *  获取角色列表
     * @param params 前端可以传值模糊查询
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String,Object> params){
        //判断是不是超级管理员 如果不是只能查询自己创建的角色
        if (getUserId() != Constant.SUPER_ADMIN){
            params.put("createUserId",getUserId());
        }

        PageUitls page = roleService.selectAllRoleList(params);
        return R.ok().put("page",page);
    }

    /**
     *  获取角色列表
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        Map<String,Object> map = new HashMap<>();
        //判断当前是否是管理员 不是则查询自己创建的角色
        if (getUserId() != Constant.SUPER_ADMIN){
            map.put("create_user_id",getUserId());
        }
        List<SysRoleEntity> list = roleService.listByMap(map);
        return R.ok().put("list",list);
    }

    /**
     *  获取角色信息
     * @param roleId
     * @return
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable Integer roleId){

        //根据id查询角色信息
        SysRoleEntity roleEntity = roleService.getById(roleId);
        //查询出所对应的menu
        List<Integer> menuList = roleMenuService.selectAllMenuIdById(roleId);
        roleEntity.setMenuIdList(menuList);

        return R.ok().put("role",roleEntity);
    }

    @RequestMapping("/add")
    @RequiresPermissions("sys:role:add")
    public R addRole(@RequestBody SysRoleEntity roleEntity){
        if (roleEntity == null){
            return R.error("提交数据有误,请重新提交!");
        }
        //设置创建人
        roleEntity.setCreateUserId(getUserId());
        //查询数据库中是否有该角色
         SysRoleEntity role = roleService.selectByRoleName(roleEntity);
        //进行新增数据
        if (role == null){
            boolean f = roleService.addRole(roleEntity);
            if (f){
                return R.ok("新增角色成功!");
            }
            return R.error("添加角色异常,请重试!");
        }
        return R.error("该角色已经存在!");
    }

    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity roleEntity){
        if (roleEntity == null){
            R.error("提交数据有误,请重新提交!");
        }
        //设置修改人
        roleEntity.setUpdateUserId(getUserId());
        boolean f = roleService.updateRole(roleEntity);
        if (f){
            return R.ok("修改角色信息成功!");
        }
        return R.ok("修改角色异常,请重试!");
    }

    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Integer[] idList){
        boolean f = roleService.removeByIds(idList);
        if (f){
            return R.ok("已经删除该角色!");
        }
        return R.ok("删除异常!请重试!") ;
    }
}
