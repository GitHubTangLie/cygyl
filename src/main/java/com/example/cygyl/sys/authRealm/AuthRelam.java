package com.example.cygyl.sys.authRealm;

import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import com.example.cygyl.sys.service.ShiroService;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.sys.service.SysUserTokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @description:  认证以及授权逻辑
 * @author: Tanglie
 * @time: 2021/3/8
 */
@Component
public class AuthRelam extends AuthorizingRealm {

    @Autowired
    ShiroService shiroService;

    public boolean supports(AuthenticationToken token){

            return token instanceof AuthToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了===============》授权逻辑");
        //获取用户基础信息
         SysUserEntity userEntity = (SysUserEntity) principals.getPrimaryPrincipal();

         //查询该用户的权限
        Set<String> permsSet = shiroService.selectUserPermission(userEntity.getId());
        System.out.println("获取到该用户的权限☆----------》" + permsSet);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了===============》认证逻辑");
        //获取Token
        String accessToken = (String) token.getPrincipal();

        //更具token查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.selectByToken(accessToken);
        //判断token是否过期
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效,请重新登录!");
        }
        System.out.println("doGetAuthenticationInfo 查询到Token" + tokenEntity.getToken());

        //根据token 查询用户信息
        SysUserEntity user =  shiroService.selectUserById(tokenEntity.getUserId());
        System.out.println("doGetAuthenticationInfo 查询到用户" + user);
        if (user.getStatus() == 0){
            throw new LockedAccountException("账号已锁定,请联系管理员!");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,accessToken,getName());

        return info;
    }
}
