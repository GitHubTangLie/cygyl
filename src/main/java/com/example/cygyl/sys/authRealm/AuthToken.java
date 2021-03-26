package com.example.cygyl.sys.authRealm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: 生成 Token
 * @author: Tanglie
 * @time: 2021/3/9
 */
public class AuthToken implements AuthenticationToken {
    String token;

    public AuthToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
