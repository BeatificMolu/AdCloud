package com.pyp.ad.mysql.dto;

import com.pyp.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 16:47
 * @modifier:
 * @version: V1.0
 */
@Data
public class ParseTemplate {
    private String database;
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template template) {
        ParseTemplate parseTemplate = new ParseTemplate();
        parseTemplate.setDatabase(template.getDatabase());
        for (JsonTable table : template.getTableList()) {
            String name = table.getTableName();
            Integer level = table.getLevel();
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            table.setLevel(level);
            parseTemplate.getTableTemplateMap().put(name, tableTemplate);
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(OpType.DELETE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
        }
        return parseTemplate;
    }

    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
