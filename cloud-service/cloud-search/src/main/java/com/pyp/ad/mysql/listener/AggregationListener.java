package com.pyp.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.pyp.ad.mysql.TemplateHolder;
import com.pyp.ad.mysql.dto.BinlogRowData;
import com.pyp.ad.mysql.dto.TableTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 23:00
 * @modifier:
 * @version: V1.0
 */
@Data
@Component
@Slf4j
public class AggregationListener implements BinaryLogClient.EventListener {
    private String dbName;
    private String tableName;
    private Map<String, Ilistener> listenerMap = new HashMap<>();
    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, Ilistener ilistener) {
        log.info("register : {}-{}", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName, _tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();
        log.debug("event type: {}", eventType);
        if (EventType.TABLE_MAP.equals(eventType)) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }
        if (!EventType.EXT_UPDATE_ROWS.equals(eventType) && !EventType.EXT_WRITE_ROWS.equals(eventType) && !EventType.EXT_DELETE_ROWS.equals(eventType)) {
            //不是业务需要的类型，直接返回
            return;
        }
        //表名和库名是否已经完成填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }
        //找出对应表有兴趣的监听器
        String key = genKey(this.dbName, this.tableName);
        Ilistener ilistener = this.listenerMap.get(key);
        if (null == ilistener) {
            log.debug("skip {}", key);
            return;
        }
        try {
            BinlogRowData rowData = builderRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(eventType);
            ilistener.onEvent(rowData);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData data) {
        if (data instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) data).getRows();
        }
        if (data instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) data).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        if (data instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) data).getRows();
        }
        return Collections.emptyList();
    }

    private BinlogRowData builderRowData(EventData eventData) {
        TableTemplate tableTemplate = templateHolder.getTableTemplate(tableName);
        if (null == tableTemplate) {
            log.warn("table {} not found", tableName);
            return null;
        }
        //一行数据的多列 列名（key）->更新的值（value）
        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int colLen = after.length;
            for (int ix = 0; ix < colLen; ix++) {
                //取出当前位置对应的列名，这里有个问题，PosMap的key是数据库的ordinal_position，这里查的是更新列值数组的下标，这两个key一样的吗？
                String colName = tableTemplate.getPosMap().get(ix);
                //如果没有则说明不关心这个列
                if (StringUtils.isEmpty(colName)) {
                    log.debug("ignore position {}", colName);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData data = new BinlogRowData();
        data.setAfter(afterMapList);
        data.setTable(tableTemplate);
        return data;
    }
}
