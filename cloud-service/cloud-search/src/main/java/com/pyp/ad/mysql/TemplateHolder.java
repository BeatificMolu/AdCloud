package com.pyp.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.mysql.constant.OpType;
import com.pyp.ad.mysql.dto.ParseTemplate;
import com.pyp.ad.mysql.dto.TableTemplate;
import com.pyp.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 21:43
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class TemplateHolder {
    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;
    private final String SQL_SCHEMA = "SELECT table_schema, table_name,column_name,ordinal_position FROM information_schema.COLUMNS WHERE table_schema=? AND table_name=?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TableTemplate getTableTemplate(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    private void loadJson(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        try {
            Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (Exception e) {
            log.error("load json error:" + e.getMessage());
        }
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);
            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()}, (rs, i) -> {
                String colName = rs.getString("COLUMN_NAME");
                if ((null != updateFields && updateFields.contains(colName))
                        || (null != updateFields && updateFields.contains(colName))
                        || (null != updateFields && updateFields.contains(colName))) {
                    //需要判断当前列是否需要索引的
                    int pos = rs.getInt("ORDINAL_POSITION");
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }
}
