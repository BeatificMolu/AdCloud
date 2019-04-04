package com.pyp.ad.index.interest;

import com.pyp.ad.index.IndexAware;
import com.pyp.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/4 22:11
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {
    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void put(String key, Set<Long> value) {
        log.info("unitItIndex: before put: {}", itUnitMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for (Long unitId : value) {
            Set<String> its = CommonUtils.getorCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("unitItIndex, after put: {}", itUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("unitItIndex: not support update: {}", itUnitMap);
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitItIndex: before put: {}", itUnitMap);
        Set<Long> units = CommonUtils.getorCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        units.removeAll(value);
        for (Long unit : value) {
            Set<String> its = CommonUtils.getorCreate(unit, unitItMap, ConcurrentSkipListSet::new);
            its.remove(key);
        }
        log.info("unitItIndex: after put: {}", itUnitMap);
    }

    public boolean match(Long unitId, List<String> itTags) {
        if (unitItMap.containsKey(unitId)) {
            Set<String> tags = unitItMap.get(unitId);
            if (CollectionUtils.isNotEmpty(unitItMap.get(tags))) {
                return CollectionUtils.isSubCollection(itTags, tags);
            }
        }
        return false;
    }
}
