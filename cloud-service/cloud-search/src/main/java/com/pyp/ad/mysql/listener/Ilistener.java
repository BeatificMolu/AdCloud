package com.pyp.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.Event;
import com.pyp.ad.mysql.dto.BinlogRowData;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 22:58
 * @modifier:
 * @version: V1.0
 */
public interface Ilistener {
    void register();

    void onEvent(BinlogRowData data);
}
