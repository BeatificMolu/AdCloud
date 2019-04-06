package com.pyp.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.pyp.ad.mysql.constant.Constant;
import com.pyp.ad.mysql.constant.OpType;
import com.pyp.ad.mysql.dto.BinlogRowData;
import com.pyp.ad.mysql.dto.MySqlRowData;
import com.pyp.ad.mysql.dto.TableTemplate;
import com.pyp.ad.sender.ISender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/7 1:19
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {
    private AggregationListener aggregationListener;
    @Resource(name = "")
    private ISender iSender;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> {
            aggregationListener.register(v, k, this);
        });
    }

    @Override
    public void onEvent(BinlogRowData data) {
        TableTemplate table = data.getTable();
        EventType eventType = data.getEventType();
        //包装成需要投递的数据
        MySqlRowData mySqlRowData = new MySqlRowData();
        mySqlRowData.setLevel(table.getLevel());
        mySqlRowData.setType(OpType.to(eventType));
        mySqlRowData.setTableName(table.getTableName());
        List<String> fieldList = table.getOpTypeFieldSetMap().get(mySqlRowData.getType());
        if (null == fieldList) {
            log.warn("{} not support for {}", mySqlRowData.getType(), table.getTableName());
            return;
        }
        for (Map<String, String> afterMap : data.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            mySqlRowData.getFieldValueMap().add(_afterMap);
        }
        iSender.sender(mySqlRowData);
    }
}
