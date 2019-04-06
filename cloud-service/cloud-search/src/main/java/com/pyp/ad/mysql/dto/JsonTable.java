package com.pyp.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 16:40
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {
    private String tableName;
    private Integer level;
    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {
        private String column;
    }
}
