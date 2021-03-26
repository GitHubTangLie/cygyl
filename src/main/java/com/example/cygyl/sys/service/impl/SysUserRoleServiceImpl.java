package com.example.cygyl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.sys.entity.SysUserRoleEntity;
import com.example.cygyl.sys.repository.SysUserRoleMapper;
import com.example.cygyl.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/23
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    /**
     *   根据传进来的id和角色列表更新
     * @param userId
     * @param roleIdList
     */
    @Override
    public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {

        this.deleteByIds(new Integer[]{userId});
        if (roleIdList == null || roleIdList.size() == 0){
            return;
        }

        //保存用户的角色信息
        for (Integer roleId : roleIdList){
            SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            this.save(userRoleEntity);
        }
    }

    /**
     *  删除用户与角色关系
     * @param idList
     * @return
     */
    @Override
    public boolean deleteByIds(Integer[] idList) {
        return baseMapper.deleteByIds(idList);
    }
}
