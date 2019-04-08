package com.pyp.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.dump.table.*;
import com.pyp.ad.handler.LevelDataHandler;
import com.pyp.ad.index.DataLevel;
import com.pyp.ad.mysql.constant.Constant;
import com.pyp.ad.mysql.dto.MySqlRowData;
import com.pyp.ad.sender.ISender;
import com.pyp.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.requests.AbstractRequestResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 21:14
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData data) {
        String level = data.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            Level2RowData(data);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            Level3RowData(data);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            Level4RowData(data);
        } else {
            log.error("IndexSender sender error: {}", JSON.toJSON(data));
        }
    }

    private void Level2RowData(MySqlRowData data) {
        if (data.getTableName().equals(Constant.PLAN_TABLE_INFO.TABLE_NAME)) {
            List<PlanTable> planTableList = new ArrayList<>();
            for (Map<String, String> fieldValueMap : data.getFieldValueMap()) {
                PlanTable table = new PlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.PLAN_TABLE_INFO.COLUMN_ID:
                            table.setId(Long.valueOf(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_END_DATE:
                            table.setEndDate(CommonUtils.parseStrinDate(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_START_DATE:
                            table.setStartDate(CommonUtils.parseStrinDate(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_USER_ID:
                            table.setUserId(Long.valueOf(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            table.setPlanStatus(Integer.valueOf(v));
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                planTableList.add(table);
            }
            planTableList.forEach(p -> LevelDataHandler.handleLevel2(p, data.getType()));
        } else if (data.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            List<CreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                CreativeTable table = new CreativeTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            table.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            table.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            table.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            table.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            table.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            table.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            table.setAdUrl(v);
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                creativeTables.add(table);
            }
            creativeTables.forEach(c -> {
                LevelDataHandler.handleLevel2(c, data.getType());
            });
        }
    }

    private  void Level3RowData(MySqlRowData data) {
        if (data.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<UnitTable> unitTables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                UnitTable table = new UnitTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            table.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            table.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            table.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            table.setUnitStatus(Integer.valueOf(v));
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                unitTables.add(table);
            }
            unitTables.forEach(u -> {
                LevelDataHandler.handleLevel3(u, data.getType());
            });
        } else if (data.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<CreativeUnitTable> tables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                CreativeUnitTable table = new CreativeUnitTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            table.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            table.setUnitId(Long.valueOf(v));
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                tables.add(table);
            }
            tables.forEach(t -> LevelDataHandler.handleLevel3(t, data.getType()));
        }
    }

    private  void Level4RowData(MySqlRowData data) {
        if (data.getTableName().equals(Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME)) {
            List<UnitDistrictTable> tables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                UnitDistrictTable table = new UnitDistrictTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                            table.setCity(v);
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                            table.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                            table.setProvince(v);
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                tables.add(table);
            }
            tables.forEach(u -> LevelDataHandler.handleLevel4(u, data.getType()));
        } else if (data.getTableName().equals(Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME)) {
            List<UnitItTable> tables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                UnitItTable table = new UnitItTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                            table.setItTag(v);
                            break;
                        case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                            table.setUnitId(Long.valueOf(v));
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                tables.add(table);
            }
            tables.forEach(u -> LevelDataHandler.handleLevel4(u, data.getType()));
        } else if (data.getTableName().equals(Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME)) {
            List<UnitKeywordTable> tables = new ArrayList<>();
            for (Map<String, String> map : data.getFieldValueMap()) {
                UnitKeywordTable table = new UnitKeywordTable();
                map.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                            table.setKeyword(v);
                            break;
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                            table.setUnitId(Long.valueOf(v));
                            break;
                        default:
                            log.error(data.getTableName() + " has not support column: {}", data);
                    }
                });
                tables.add(table);
            }
            tables.forEach(u -> LevelDataHandler.handleLevel4(u, data.getType()));
        }
    }
}
