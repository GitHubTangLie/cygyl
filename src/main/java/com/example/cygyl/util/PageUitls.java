package com.example.cygyl.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @description:  分页工具类
 * @author: Tanglie
 * @time: 2021/3/17
 */
public class PageUitls implements Serializable {
    private static final long serialVersionUID = 1l;
    /**总页数*/
    private int totalCount;
    /**每页记录数*/
    private int pageSize;
    /**总页数*/
    private int totalPage;
    /**当前页数*/
    private int currPage;
    /**数据*/
    private List<?> list;

    /** 
    * @Description:  分页工具
    * @Param: [list, totalCount, pageSize, currPage]
    * @return: 
    * @Author: TangLie
    * @Date: 2021/3/17
    */
    public PageUitls(List<?> list,int totalCount,int pageSize,int currPage){
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount/pageSize);
    }

    /**
     *  分页
     */

    public PageUitls(IPage<?> page){
        this.list = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }


    public int getTotalCount() {
        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
