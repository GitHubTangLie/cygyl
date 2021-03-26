package com.example.cygyl.enums;

import lombok.Getter;
import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/3/8 13:10
 */
@Getter
public enum CategoryStatusEnum {
    OFF(0,"下架"),ON(1,"上架");
    private Integer code;
    private String name;

    CategoryStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CategoryStatusEnum toType(int code) {
        return Stream.of(CategoryStatusEnum.values())
                .filter(c -> c.code == code)
                .findAny()
                .orElse(null);
    }
}
