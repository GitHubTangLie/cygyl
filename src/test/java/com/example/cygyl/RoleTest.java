package com.example.cygyl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cygyl.sys.entity.SysRoleEntity;
import com.example.cygyl.sys.entity.SysRoleMenuEntity;
import com.example.cygyl.sys.repository.SysRoleMenuMapper;
import com.example.cygyl.sys.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/23
 */

@SpringBootTest
public class RoleTest {


    @Autowired
    SysRoleService roleService;
    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    @Test
    public void query(){
        SysRoleEntity roleEntity = new SysRoleEntity();
        roleEntity.setRoleName("供应商员工A");
        SysRoleEntity role = roleService.query().eq("role_name",roleEntity.getRoleName()).one();
        System.out.println(role);

    }

    @Test
    public void contanisAll(){
        List<Integer> list1 = new ArrayList<>();
            list1.add(1);
            list1.add(2);
            list1.add(3);

        List<Integer> list2 = new ArrayList<>();
            list2.add(1);
            list2.add(2);
            list2.add(3);
            list2.add(4);

            //list1 所有元素中包含list2 测等于true
        if(!list1.containsAll(list2)){
            System.out.println("没有越权!");
        }else {
            System.out.println("已经越权处理!");
        }


    }

    @Test
    public void roleList(){
        List<SysRoleMenuEntity> list = roleMenuMapper.selectList(
                new QueryWrapper<SysRoleMenuEntity>()
                        .eq("role_id",2));

        for (SysRoleMenuEntity roleMenuEntity : list){
            System.out.println(roleMenuEntity);
        }
    }
}
