package com.pyp.ad.constant;

import lombok.Getter;

/**
 * @description: 创意的类型
 * @author: yy
 * @data: Created in  2019/4/1 23:13
 * @modifier:
 * @version: V1.0
 */
@Getter
public enum CreativeType {
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");
    private Integer type;
    private String desc;

    CreativeType(Integer type, String desc) {
        this.desc = desc;
        this.type = type;
    }
}
