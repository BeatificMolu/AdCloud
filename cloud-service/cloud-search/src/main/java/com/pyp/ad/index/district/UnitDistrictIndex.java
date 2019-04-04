package com.pyp.ad.index.district;

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
 * @data: Created in  2019/4/4 22:29
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {
    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void put(String key, Set<Long> value) {
        log.info("unitDistrictIndex: before put: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for (Long unitId : value) {
            Set<String> keywords = CommonUtils.getorCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            keywords.add(key);
        }
        log.info("unitDistrictIndex: after put: {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("unitDistrictIndex: not support update: {}", unitDistrictMap);
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitDistrictIndex: before delete: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> keywords = CommonUtils.getorCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            keywords.remove(key);
        }
        log.info("unitDistrictIndex: after delete: {}", unitDistrictMap);
    }

    public boolean match(Long unitId, List<String> districts) {
        if (unitDistrictMap.containsKey(unitId)) {
            Set<String> keywords = unitDistrictMap.get(unitId);
            if (CollectionUtils.isNotEmpty(keywords)) {
                return CollectionUtils.isSubCollection(districts, keywords);
            }
        }
        return false;
    }
}
