package com.example.cygyl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.sys.authRealm.TokenGenerator;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import com.example.cygyl.sys.repository.SysUserTokenMapper;
import com.example.cygyl.sys.service.SysUserTokenService;
import com.example.cygyl.util.R;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description: Token 实现
 * @author: Tanglie
 * @time: 2021/3/9
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserTokenEntity> implements SysUserTokenService {
    //设置为2 小时过期
    private final static int EXPIRE = 3600 * 2;

    //实现token
    @Override
    public R createToken(Integer userId) {
        //生成Token
        String token = TokenGenerator.generateValue();
        System.out.println("SysUserTokenServiceImpl ---- 生成的Token为："+ token );

        //当前时间
        Date now = new Date();
        //过期时间

        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null){
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保持到数据库
            this.save(tokenEntity);
        }else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新Token
            this.updateById(tokenEntity);
        }

        R r = R.ok().put("token",token).put("expire",EXPIRE);
        return r;
    }

    @Override
    public void logout(Integer userId) {
        //生成一个新的tokne
        String token = TokenGenerator.generateValue();
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();

        //更新token值
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
