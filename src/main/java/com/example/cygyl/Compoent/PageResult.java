package com.example.cygyl.Compoent;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.cygyl.util.BeanMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/3/9 14:55
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResult<T>{

    //总记录数
    private Long total;
    //当前页
    private Long current;
    //每页显示条数
    private Long size;
    //结果集
    private List<T> items;

    public PageResult(IPage<T> page) {
        this.total = page.getTotal();
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.items = page.getRecords();
    }

}
