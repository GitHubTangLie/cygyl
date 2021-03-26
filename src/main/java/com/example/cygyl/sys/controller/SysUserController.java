package com.example.cygyl.sys.controller;


import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.form.SysPasswordForm;
import com.example.cygyl.sys.service.SysRoleService;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.util.Constant;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.R;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* @Description: 系统用户相关
* @Param:
* @return:  返回关于用户基本操作功能
* @Author: TangLie
* @Date: 2021/3/12
*/
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController{

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;



    /**
     *  所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String,Object> params){
        System.out.println("userList value ☆----------->"+ params);
        if (getUserId() != Constant.SUPER_ADMIN){
            params.put("createUserId",getUserId());
        }
        PageUitls page =  userService.selectPage(params);

        return R.ok().put("page",page);
    }


    /**
     *  获取登录用户相关信息
     */
    public R getUserInfo(){

        return R.ok().put("user",getUser());
    }

    /**
     *   获取用户相关信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable Integer userId){
        System.out.println(userId);
        //查询当前id用户返回
        SysUserEntity userEntity = userService.getById(userId);
        //查询角色信息返回
        List<Integer> roleList = roleService.selectRoleListById(userId);
        userEntity.setRoleIdList(roleList);

        return R.ok().put("list",userEntity);
    }
    /**
     * 修改登录用户密码
     */
    @PostMapping("/updatePwd")
    public R updatePassword(@RequestBody SysPasswordForm passwordForm){
        if (StringUtils.isBlank(passwordForm.getNewPassword())){
            return R.error("新密码不能为空!");
        }
        //对密码进行处理加密
        String password = new Sha256Hash(passwordForm.getOldPassword(),getUser().getSalt()).toHex();
        String newPassword = new  Sha256Hash(passwordForm.getNewPassword(),getUser().getSalt()).toHex();
        System.out.println("新旧密码☆------------->"+ password+newPassword);


        //数据库查询以及更新
        Boolean F = userService.updatePassword(getUserId(),password,newPassword);
        if (!F){
           return R.error("输入原密码错误!");
        }
        return R.ok();
    }


    /**
     * 新增用户信息
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    public R addUser(@RequestBody SysUserEntity userEntity) {
        if (userEntity == null) {
            R.error("提交数据有误,请重新提交");
        }
        //判断是否为重复添加
        SysUserEntity user = userService.selectByName(userEntity.getUsername());
        if (user == null) {
            //添加到数据库
            userEntity.setCreateUserId(getUserId());
            boolean f = userService.insertUser(userEntity);
            if (f) {
                return R.ok("添加管理员成功!");
            }
            return R.error("添加用户异常,请检查数据!");
        } else {
            return R.error("请勿重复添加管理员!");
        }
    }

    /**
     * 修改用户信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R updateUser(@RequestBody SysUserEntity userEntity){
        if (userEntity == null){
            R.error("提交数据有误,请重新提交");
        }
        //设置设修改的人
        userEntity.setUpdateUserId(getUserId());
        boolean f = userService.updateUserInfo(userEntity);
        if (f){
            return R.ok("修改成功!");
        }
        return R.error("修改异常,请检查数据!");
    }
    /**
     *  删除用户信息
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R deleteUser(@RequestBody Integer[] idList){
       if (ArrayUtils.contains(idList,Constant.SUPER_ADMIN)){
           return R.error("不能删除系统管理员!");
       }
       if (ArrayUtils.contains(idList,getUserId())){
           return R.error("不能删除当前用户!");
       }
        boolean f = userService.deletesUserInfo(idList);
       if (f){
           return R.ok("删除成功!");
       }
       return R.error("不存在当前用户,请重试!");
    }

}
