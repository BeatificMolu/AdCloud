package com.pyp.ad.index;

import lombok.Getter;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/10 23:12
 * @modifier:
 * @version: V1.0
 */
@Getter
public enum CommonStatus {
    VALID(1, "有效状态"),
    INVAL(1, "无效状态");
    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
