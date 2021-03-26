package com.example.cygyl;

import com.example.cygyl.sys.entity.SysMenuEntity;
import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.repository.SysRoleMapper;
import com.example.cygyl.sys.service.ShiroService;
import com.example.cygyl.sys.service.SysMenuService;
import com.example.cygyl.sys.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/17
 */
@SpringBootTest
public class UserTest {

    @Autowired
    ShiroService shiroService;

    @Autowired
    SysMenuService menuService;

    @Autowired
    SysUserService userService;
    @Autowired
    SysRoleMapper roleMapper;

    @Test
    public void  testGetPermission(){

        Set<String> slist = shiroService.selectUserPermission(2);
        SysUserEntity  user = shiroService.selectUserById(1);

        System.out.println("user ---------------->" +  user);
        for (String perms : slist){
            System.out.println("权限--------------->"+perms);
        }

    }

    @Test
    public void testGetMeun(){
       List<SysMenuEntity> menuList =  menuService.getUserMenuList(2);
       for (SysMenuEntity menu : menuList){

           System.out.println("菜单ID---------------->"+ menu);
       }
    }

    @Test
    public void testUpdatePaswword(){
        boolean F = userService.updatePassword(7,"admin","123");
        if (F){
            System.out.println("update ok!");
        }else {
            System.out.println("update error！");
        }
    }

    @Test
    public void delete(){
        Integer[] integer = new Integer[]{0} ;
        boolean f = userService.deletesUserInfo(integer);
        if (f){
            System.out.println("delete ok!");
        }else {
            System.out.println("delete error！");
        }
    }
    @Test
    public void update(){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setId(23);
        userEntity.setUsername("mosi");
        userEntity.setPassword("123456");
        userEntity.setPhone("13725264679");
        userEntity.setStatus(1);
        userEntity.setCompanyId(1);
        boolean f = userService.updateUserInfo(userEntity);
        if (f){
            System.out.println("update ok!");
        }else {
            System.out.println("update error！");
        }
    }
    @Test
    public void insert(){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUsername("mosi");
        userEntity.setPassword("123456");
        userEntity.setPhone("13725264679");
        userEntity.setStatus(1);
        userEntity.setCompanyId(1);
        boolean f = userService.insertUser(userEntity);
        if (f){
            System.out.println("insert ok!");
        }else {
            System.out.println("insert error！");
        }
    }

    @Test
    public void time(){
        System.out.println(new Date());
    }

    @Test
    public void rolelist(){
        System.out.println("查询到的资料------------》"+roleMapper.selectRoleListById(5));
    }
}
