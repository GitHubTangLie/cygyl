package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.util.PageUitls;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户service层
 * @author: Tanglie
 * @time: 2021/3/9
 */
public interface SysUserService extends IService<SysUserEntity>{
    /**
     * 根据用户电话号码查询用户信息
     * @param phone
     * @return SysUserEntity
     */
    SysUserEntity selectByPhone(String phone);

    /**
     *  查询用户的所有权限
     */
    List<String> selectAllPermission(Integer userId);

    /**
     *  查询用户的所有菜单ID
     */
    List<Integer> selectAllMenuIdById(Integer userId);

    /**
     * 根据用户名查询系统的用户
     */
    SysUserEntity selectByName(String username);

    /**
     *  根据创建id 查询列表
     * @param params
     * @return
     */
    PageUitls selectPage(Map<String, Object> params);

    /**
     *  更新用户密码
     * @param user_id
     * @param password
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Integer user_id, String password, String newPassword);


    /**
     *  删除用户信息，传入一个数组进行删除
     * @param deletes
     * @return
     */
    boolean deletesUserInfo(Integer[] deletes);

    /**
     *   修改用户信息.
     * @param userEntity
     * @return
     */
    boolean updateUserInfo(SysUserEntity userEntity);

    /**
     *   添加管理
     * @param userEntity
     * @return
     */
    boolean insertUser(SysUserEntity userEntity);

}
