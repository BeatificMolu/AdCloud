package com.pyp.ad.handler;

import com.pyp.ad.dump.table.*;
import com.pyp.ad.index.DataTable;
import com.pyp.ad.index.IndexAware;
import com.pyp.ad.index.creative.CreativeIndex;
import com.pyp.ad.index.creative.CreativeObject;
import com.pyp.ad.index.creativeunit.CreativeUnitIndex;
import com.pyp.ad.index.creativeunit.CreativeUnitObject;
import com.pyp.ad.index.district.UnitDistrictIndex;
import com.pyp.ad.index.district.UnitDistrictObject;
import com.pyp.ad.index.interest.UnitItIndex;
import com.pyp.ad.index.keyword.UnitKeywordIndex;
import com.pyp.ad.index.plan.PlanIndex;
import com.pyp.ad.index.plan.PlanObject;
import com.pyp.ad.index.unit.UnitIndex;
import com.pyp.ad.index.unit.UnitObject;
import com.pyp.ad.mysql.constant.OpType;
import com.pyp.ad.utils.CommonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:1.索引之间存在着层级关系，也就是依赖关系的划分 2加载全量索引其实是增量索引“添加”的一种特殊实现
 * @author: yy
 * @data: Created in  2019/4/5 21:19
 * @modifier:
 * @version: V1.0
 */
@Slf4j
public class LevelDataHandler {
    public static void handleLevel2(PlanTable table, OpType type) {
        PlanObject object = new PlanObject(table.getId(), table.getUserId(), table.getPlanStatus(), table.getStartDate(), table.getEndDate());
        handleBinLogEvent(DataTable.of(PlanIndex.class),
                object.getPlanId(),
                object,
                type
        );
    }

    public static void handleLevel2(CreativeTable table, OpType type) {
        CreativeObject object = new CreativeObject(table.getAdId(), table.getName(), table.getType(), table.getMaterialType(), table.getHeight(), table.getWidth(), table.getAuditStatus(), table.getAdUrl());
        handleBinLogEvent(DataTable.of(CreativeIndex.class),
                object.getAdId(),
                object,
                type
        );
    }

    public static void handleLevel3(UnitTable table, OpType type) {
        PlanObject planObject = DataTable.of(PlanIndex.class).get(table.getPlanId());
        if (null == planObject) {
            log.error("handleLevel3 found PlanObject error: {}", table.getPlanId());
            return;
        }
        UnitObject object = new UnitObject(table.getUnitId(), table.getUnitStatus(), table.getPositionType(), planObject.getPlanId(), planObject);
        handleBinLogEvent(DataTable.of(UnitIndex.class),
                object.getUnitId(),
                object,
                type
        );
    }

    public static void handleLevel3(CreativeUnitTable table, OpType type) {
        if (type.equals(OpType.UPDATE)) {
            log.error("handleLevel3 creativeUnitTable can not support update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            return;
        }
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(table.getAdId());
        if (creativeObject == null) {
            log.error("handleLevel3 CreativeUnitTable can not find creativeObject");
            return;
        }
        CreativeUnitObject object = new CreativeUnitObject(
                table.getAdId(),
                table.getUnitId()
        );
        handleBinLogEvent(DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(table.getAdId().toString(), table.getUnitId().toString()),
                object,
                type
        );

    }

    public static void handleLevel4(UnitDistrictTable table, OpType type) {
        if (OpType.UPDATE.equals(type)) {
            log.error("unitDistrictTable can not support update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            log.error("unitDistrictTable can not find unitObject");
            return;
        }
        String key = CommonUtils.stringConcat(table.getProvince(), table.getCity());
        Set<Long> value = new HashSet<>(
                Collections.singleton(table.getUnitId())
        );
        handleBinLogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(UnitItTable table, OpType type) {
        if (OpType.UPDATE.equals(type)) {
            log.error("handleLevel4 UnitItTable can not support update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(table.getUnitId());
        if (unitObject == null) {
            log.error("handleLevel4 UnitItTable can not find UnitObject");
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(table.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitItIndex.class), table.getItTag(), value, type);
    }

    public static void handleLevel4(UnitKeywordTable table, OpType type) {
        if (OpType.UPDATE.equals(type)) {
            log.error("handleLevel4 UnitKeyword can not support update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(table.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4 unitKeyword can not find unitObject");
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(table.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitKeywordIndex.class), table.getKeyword(), value, type);
    }

    private static <K, V> void handleBinLogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.put(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            default:
                break;
        }
    }
}
