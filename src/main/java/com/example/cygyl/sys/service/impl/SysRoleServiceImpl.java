package com.example.cygyl.sys.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cygyl.core.exception.CustomRunTimeException;
import com.example.cygyl.sys.entity.SysRoleEntity;
import com.example.cygyl.sys.repository.SysRoleMapper;
import com.example.cygyl.sys.repository.SysUserMapper;
import com.example.cygyl.sys.repository.SysUserRoleMapper;
import com.example.cygyl.sys.service.SysRoleMenuService;
import com.example.cygyl.sys.service.SysRoleService;
import com.example.cygyl.util.Constant;
import com.example.cygyl.util.PageUitls;
import com.example.cygyl.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:角色管理实现层接口
 * @author: Tanglie
 * @time: 2021/3/22
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {


    @Autowired
    SysRoleMapper roleMapper;

    /**
     *  循环依赖问题: 如果SysUserServiceImpl 中注入了bean SysRoleService 在本 SysRoleServiceImpl中也注入了bean SysUserService 产生循环依赖异常 战士解决办法 将bean 换为SysUserMapper
     */
    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysRoleMenuService roleMenuService;



    /**
     *  查询角色列表具体实现
     * @param params
     * @return
     */
    @Override
    public PageUitls selectAllRoleList(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Integer createUserId = (Integer)params.get("createUserId");

        //分页查询返回结果集
        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(params),
                new QueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName),"role_name",roleName)
                        .eq(createUserId != null,"create_user_id",createUserId)
        );

        return new PageUitls(page);
    }

    /**
     *  通过id查询角色实现
     * @param userId
     * @return
     */
    @Override
    public List<Integer> selectRoleListById(Integer userId) {

        return roleMapper.selectRoleListById(userId);
    }

    /**
     *  批量删除
     * @param idList
     * @return
     */
    @Override
    public boolean removeByIds(Integer[] idList) {

        //删除当前用户里面的role
        boolean f = this.removeByIds(Arrays.asList(idList));

        //删除此角色与用户之间的关联关系
        f = userRoleMapper.deleteByIds(idList);
        //删除此角色与菜单之间的关联关系
       f = roleMenuService.deleteByIds(idList);

        return f;
    }

    @Override
    public SysRoleEntity selectByRoleName(SysRoleEntity roleEntity) {
        return roleMapper.selectOne(
                new QueryWrapper<SysRoleEntity>()
                        .eq("create_user_id",roleEntity.getCreateUserId())
                        .eq("role_name",roleEntity.getRoleName()));
    }

    /**
     *  添加角色信息实现
     * @param roleEntity
     * @return
     */
    @Override
    public boolean addRole(SysRoleEntity roleEntity) {
        roleEntity.setCreateTime(new Date());
        boolean f = this.save(roleEntity);

        //检查是否越权
        checkPermis(roleEntity);
        //保存角色与菜单的关联
       roleMenuService.saveOrUpdate(roleEntity.getRoleId(),roleEntity.getMenuIdList());

        return f;
    }

    /**
     *  修改角色信息
     * @param roleEntity
     * @return
     */
    @Override
    public boolean updateRole(SysRoleEntity roleEntity) {

        roleEntity.setUpdateTime(new Date());
        boolean f = this.updateById(roleEntity);

        //检查是否越权
        checkPermis(roleEntity);

        //保存角色和菜单关系
        roleMenuService.saveOrUpdate(roleEntity.getRoleId(),roleEntity.getMenuIdList());

        return f;
    }

    /**
     *   检查是否越权赋值权限
     * @param roleEntity
     */
    private void checkPermis(SysRoleEntity roleEntity) {
        //判断是不是管理员如果不是管理则查询自己的菜单权限
        if (roleEntity.getCreateUserId() == Constant.SUPER_ADMIN){
            return;
        }
        if (roleEntity.getMenuIdList() == null || roleEntity.getMenuIdList().size() == 0){
            return;
        }

        //查询自己的能授权的menuId
         List<Integer> menuIdList = userMapper.selectAllMenuIdById(roleEntity.getCreateUserId());
        //查询出的自身登录人的权限和所赋值的权限不相同则代表越权
        if (!menuIdList.containsAll(roleEntity.getMenuIdList())){
            throw new CustomRunTimeException("新增的角色权限,超出你的权限范围!");
        }

    }


}
