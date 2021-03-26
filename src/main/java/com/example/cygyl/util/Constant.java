package com.example.cygyl.util;

/**
 * @description: 常量定义工具类
 * @author: Tanglie
 * @time: 2021/3/16
 */
public class Constant {

    /** 超级管理员ID*/
    public static final int SUPER_ADMIN = 1;
    /** 当前页码*/
    public static  final String PAGE = "page";
    /** 每页显示的分页记录*/
    public static final String LIMIT = "limit";
    /** 排序字段*/
    public static final String ORDER_FIELD ="sidx";
    /** 排序*/
    public static final String ORDER = "order";
    /** 升序*/
    public static final String ASC = "asc";


    public enum MenuType{
        /** 目录 */
        CATALOG(0),

        /** 菜单 */
        MUEN(1),

        /** 按钮 */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value =  value;
        }

        public int getValue() {
            return value;
        }

    }
}
