package com.pyp.ad.index;

import lombok.Getter;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 21:09
 * @modifier:
 * @version: V1.0
 */
@Getter
public enum DataLevel {
    LEVEL2("2","LEVEL2"),
    LEVEL3("3","LEVEL3"),
    LEVEL4("4","LEVEL4");
    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
