package com.pyp.ad.mysql.dto;

import com.pyp.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 23:58
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySqlRowData {
    private String tableName;
    private String level;
    private OpType type;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
