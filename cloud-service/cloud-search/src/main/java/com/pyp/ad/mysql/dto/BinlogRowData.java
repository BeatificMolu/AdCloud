package com.pyp.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 22:56
 * @modifier:
 * @version: V1.0
 */
@Data
public class BinlogRowData {
    private TableTemplate table;
    private EventType eventType;
    private List<Map<String,String>> after;
    private List<Map<String,String>> before;
}
