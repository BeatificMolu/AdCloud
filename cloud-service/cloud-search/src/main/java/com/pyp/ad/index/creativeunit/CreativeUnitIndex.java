package com.pyp.ad.index.creativeunit;

import com.pyp.ad.index.IndexAware;
import com.pyp.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/4 23:35
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {
    /**
     * <adId-unitID,CreativeUnitObject>
     */

    private static Map<String, CreativeUnitObject> objectMap;
    /**
     * adId,Set<unitID>>
     */
    private static Map<Long, Set<Long>> creativeUnitMap;
    /**
     * unitID,Set<adId>>
     */
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void put(String key, CreativeUnitObject value) {
        objectMap.put(key, value);
        Set<Long> unitSet = creativeUnitMap.get(key);
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }
    }
}
