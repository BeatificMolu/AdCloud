package com.pyp.ad.index.unit;

import com.pyp.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 23:13
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class UnitIndex implements IndexAware<Long, UnitObject> {
    private static Map<Long, UnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        Set<Long> adUnitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (UnitObject.isAdSlotTypeOK(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    public List<UnitObject> fetch(Collection<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.EMPTY_LIST;
        }
        List<UnitObject> result = new ArrayList<>();
        unitIds.forEach(u -> {
            UnitObject object = get(u);
            if (object == null) {
                log.error("UnitObject is not found: {}", u);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public UnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void put(Long key, UnitObject value) {
        log.info("before put: {}", objectMap);
        objectMap.put(key, value);
        log.info("after put: {}", objectMap);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.info("before update: {}", objectMap);
        UnitObject oldValue = objectMap.get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
