package com.example.cygyl.sys.service.impl;

import com.example.cygyl.sys.entity.SysMenuEntity;
import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import com.example.cygyl.sys.repository.SysMenuMapper;
import com.example.cygyl.sys.repository.SysUserMapper;
import com.example.cygyl.sys.repository.SysUserTokenMapper;
import com.example.cygyl.sys.service.ShiroService;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/10
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    SysMenuMapper menuMapper;

    @Autowired
    SysUserTokenMapper tokenMapper;

    @Autowired
    SysUserMapper userMapper;

    public SysUserTokenEntity selectByToken(String token) {
        return tokenMapper.selectByToken(token);
    }

    @Override
    public SysUserEntity selectUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public Set<String> selectUserPermission(Integer userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = menuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else {
            permsList = userMapper.selectAllPermission(userId);
        }
        //用户列表 set不包含重复切无序的集合
        Set<String> permSet = new HashSet<>();
        for (String perm : permsList){
            if (StringUtils.isBlank(perm)){
                continue;
            }
            //
            permSet.addAll(Arrays.asList(perm.trim().split(",")));
        }

        return permSet;
    }
}
