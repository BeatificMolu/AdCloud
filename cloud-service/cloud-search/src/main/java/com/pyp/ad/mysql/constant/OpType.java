package com.pyp.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/5 21:25
 * @modifier:
 * @version: V1.0
 */
public enum OpType {
    ADD, UPDATE, DELETE, OTHER;

    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
