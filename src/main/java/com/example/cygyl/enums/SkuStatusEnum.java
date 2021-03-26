package com.example.cygyl.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/3/11 9:13
 */

@Getter
public enum SkuStatusEnum {
    OFF(0,"下架"),ON(1,"上架");
    private Integer code;
    private String name;

    SkuStatusEnum(Integer code,String name) {
        this.code = code;
        this.name = name;
    }

    public static SkuStatusEnum toType(int code) {
        return Stream.of(SkuStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny()
                .orElse(null);
    }
}
