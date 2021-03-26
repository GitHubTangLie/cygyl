package com.example.cygyl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cygyl.sys.entity.SysUserTokenEntity;
import com.example.cygyl.util.R;

/**
 * @description:  Token service层
 * @author: Tanglie
 * @time: 2021/3/9
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {


    /**
     *  生成Token
     * @param userId 用户id
     * @return
     */
    R createToken(Integer userId);


    /**
     * 退出 修改token值
     */

    void logout(Integer userId);

}
