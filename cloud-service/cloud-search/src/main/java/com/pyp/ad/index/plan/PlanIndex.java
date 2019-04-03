package com.pyp.ad.index.plan;

import com.pyp.ad.client.vo.Plan;
import com.pyp.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 23:01
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class PlanIndex implements IndexAware<Long, PlanObject> {
    private static  Map<Long, PlanObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public PlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void put(Long key, PlanObject value) {
        log.info("before put: {}", objectMap);
        objectMap.put(key, value);
        log.info("after put: {}", objectMap);
    }

    @Override
    public void update(Long key, PlanObject value) {
        log.info("before update: {}", objectMap);
        PlanObject oldValue = objectMap.get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, PlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
