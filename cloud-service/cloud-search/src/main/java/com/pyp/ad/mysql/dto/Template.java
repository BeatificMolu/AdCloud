package com.pyp.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 16:42
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {
    private String database;
    private List<JsonTable> tableList;
}
