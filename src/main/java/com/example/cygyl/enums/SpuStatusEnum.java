package com.example.cygyl.enums;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/3/9 11:32
 */
@Getter
public enum SpuStatusEnum {
    OFF(0,"下架"),ON(1,"上架");
    private Integer code;
    private String name;

    SpuStatusEnum(Integer code,String name) {
        this.code = code;
        this.name = name;
    }

    public static SpuStatusEnum toType(int code) {
        return Stream.of(SpuStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny()
                .orElse(null);
    }
}
