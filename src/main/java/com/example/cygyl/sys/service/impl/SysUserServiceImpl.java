package com.example.cygyl.sys.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.core.exception.CustomRunTimeException;
import com.example.cygyl.sys.entity.SysUserEntity;
import com.example.cygyl.sys.repository.SysUserMapper;
import com.example.cygyl.sys.service.SysRoleService;
import com.example.cygyl.sys.service.SysUserRoleService;
import com.example.cygyl.sys.service.SysUserService;
import com.example.cygyl.util.Constant;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.Query;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;

    /**根据自己号码查询自身信息*/
    @Override
    public SysUserEntity selectByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }


    /**查询自身所有的权限*/
    @Override
    public List<String> selectAllPermission(Integer userId) {
        return userMapper.selectAllPermission(userId);
    }

    /**通过自身id查询所有的菜单id*/
    @Override
    public List<Integer> selectAllMenuIdById(Integer userId) {
        
        return userMapper.selectAllMenuIdById(userId);
    }

    /**根据姓名查询自身信息*/
    @Override
    public SysUserEntity selectByName(String username) {
        return userMapper.selectByName(username);
    }


    /**☆ 查询本身创建的管理员 */
    @Override
    public PageUitls selectPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Integer createUserId = (Integer) params.get("createUserId");

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>().like(StringUtils.isNotBlank(username),"username",username).
                        eq(createUserId != null,"create_user_id",createUserId)
        );
        return new PageUitls(page);
    }

    /**修改密码*/
    @Override
    public Boolean updatePassword(Integer userId, String password, String newPassword) {

        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>()
                        .eq("id",userId)
                        .eq("password",password));
    }

    /**删除用户*/
    @Override
    public boolean deletesUserInfo(Integer[] deletes) {

        return this.removeByIds(Arrays.asList(deletes));
    }

    /**修改用户信息*/
    @Override
    public boolean updateUserInfo(SysUserEntity userEntity) {
        if (userEntity.getPassword() == null){
            userEntity.setPassword(null);
        }else {
            //密码加密处理
            userEntity.setPassword(new Sha256Hash(userEntity.getPassword(),userEntity.getSalt()).toHex());
        }
        //更新修改时间
        userEntity.setUpdateTime(new Date());
        //修改信息
        boolean f = this.updateById(userEntity);
        //操作对他角色进行保存 检查是否越权
        checkRole(userEntity);

        //保存用户与角色的关系
        userRoleService.saveOrUpdate(userEntity.getId(),userEntity.getRoleIdList());

        return f;
    }

    /** 添加管理员*/
    @Override
    public boolean insertUser(SysUserEntity userEntity) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        if (StringUtils.isBlank(userEntity.getPassword())){
            //设置默认密码123456
            userEntity.setPassword(new Sha256Hash("123456",salt).toHex());

        }
        userEntity.setPassword(new Sha256Hash(userEntity.getPassword(),salt).toHex());
        userEntity.setCreateTime(new Date());
        userEntity.setSalt(salt);
        boolean f = this.save(userEntity);

        //检查是否越
        checkRole(userEntity);

        // 保存用户和角色的关系
        userRoleService.saveOrUpdate(userEntity.getId(),userEntity.getRoleIdList());
        return f;
    }


    /** 检查是否越权, 查看查询的权限和传进来权限是否越权*/
    private void checkRole(SysUserEntity userEntity) {
        //查询是否存在值
        if(userEntity.getRoleIdList() == null || userEntity.getRoleIdList().size() == 0){
            return;
        }
        //是否为管理员创建,不是管理员需要查询自己创建的角色
        if (userEntity.getCreateUserId() == Constant.SUPER_ADMIN){
            return;
        }
        //查询自己创建的角色列表
        List<Integer> userRoleList =  roleService.selectRoleListById(userEntity.getCreateUserId());
        if (userEntity.getRoleIdList().containsAll(userRoleList)){
            throw new CustomRunTimeException("所修改的角色,非本人创建!");
        }
    }


}
