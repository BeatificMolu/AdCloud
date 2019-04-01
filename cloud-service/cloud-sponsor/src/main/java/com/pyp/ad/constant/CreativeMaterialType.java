package com.pyp.ad.constant;

/**
 * @description:物料的类型
 * @author: yy
 * @data: Created in  2019/4/1 23:16
 * @modifier:
 * @version: V1.0
 */
public enum CreativeMaterialType {
    JPG(1, "JPEG"),
    BMP(2, "BMP"),
    MP4(3, "MP4"),
    AVI(4, "AVI"),
    TEXT(5, "TEXT");
    private Integer type;
    private String desc;

    CreativeMaterialType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
