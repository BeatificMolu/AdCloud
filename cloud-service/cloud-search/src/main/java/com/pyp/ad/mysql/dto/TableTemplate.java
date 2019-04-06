package com.pyp.ad.mysql.dto;

import com.pyp.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 16:43
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引->字段名 监听的日志回调显示的是字段索引,需要翻译过来
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
