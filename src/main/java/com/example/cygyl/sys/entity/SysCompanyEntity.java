package com.example.cygyl.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/26
 */
@Data
@TableName("company")
public class SysCompanyEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String companyName;
    private String companyAddress;
    private String company_phone;
    private Integer ozId;


}
