package com.example.cygyl.Compoent;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import com.example.cygyl.sys.repository.SysUserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2021/3/19 15:04
 */
@Service
public class UserTokenService {
    @Autowired
    private SysUserTokenMapper userTokenMapper;

    public Integer getUserIdByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null || "".equals(token.trim()) ) {
            token = request.getParameter("token");
        }
        SysUserTokenEntity userTokenEntity =  userTokenMapper.selectOne(new QueryWrapper<SysUserTokenEntity>()
                .eq("token", token));
        return userTokenEntity.getUserId();
    }
}
