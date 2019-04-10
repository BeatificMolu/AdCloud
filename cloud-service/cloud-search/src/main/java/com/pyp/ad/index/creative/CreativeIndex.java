package com.pyp.ad.index.creative;

import com.pyp.ad.index.IndexAware;
import com.pyp.ad.index.unit.UnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/4 22:39
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void put(Long key, CreativeObject value) {
        log.info("before put: {}", objectMap);
        objectMap.put(key, value);
        log.info("after put: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before update: {}", objectMap);
        CreativeObject oldValue = objectMap.get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }

    public List<CreativeObject> fetch(Collection<Long> adIds) {
        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }
        List<CreativeObject> objects = new ArrayList<>();
        for (Long id : adIds) {
            CreativeObject object = get(id);
            if (object == null) {
                log.error("CreativeObject not found: {}", id);
                return Collections.emptyList();
            }
            objects.add(object);
        }
        return objects;
    }
}
