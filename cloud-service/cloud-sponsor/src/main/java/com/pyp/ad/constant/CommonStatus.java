package com.pyp.ad.constant;

import lombok.Getter;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 22:27
 * @modifier:
 * @version: V1.0
 */
@Getter
public enum CommonStatus {
    VALID(1, "有效状态"),
    INVALID(0, "无效状态");
    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
