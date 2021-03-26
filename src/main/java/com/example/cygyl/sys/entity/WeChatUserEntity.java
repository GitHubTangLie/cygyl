package com.example.cygyl.sys.entity;

import lombok.Data;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/15
 */
@Data
public class WeChatUserEntity {

    private String openId;
    private String nikName;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headImgUrl;

    @Override
    public String toString() {
        return "WeChatUserEntity{" +
                "openId='" + openId + '\'' +
                ", nikName='" + nikName + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
