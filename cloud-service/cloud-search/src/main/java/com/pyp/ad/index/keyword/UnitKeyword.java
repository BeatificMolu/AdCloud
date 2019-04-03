package com.pyp.ad.index.keyword;

import com.pyp.ad.index.IndexAware;
import com.pyp.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @description: 倒排索引，一个关键词对应多个推广单元
 * @author: yy
 * @data: Created in  2019/4/3 23:19
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class UnitKeyword implements IndexAware<String, Set<Long>> {
    /**
     * 关键词到推广单元的限制
     */
    private static Map<String, Set<Long>> keywordUnitMap;
    /**
     * 推广单元到关键词的限制
     */
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isNotEmpty(key)) {
            return Collections.EMPTY_SET;
        }
        Set<Long> result = keywordUnitMap.get(key);
        if (result == null) {
            return Collections.EMPTY_SET;
        }
        return result;
    }

    @Override
    public void put(String key, Set<Long> value) {
        log.info("UnitKeywordIndex,before put: {}", unitKeywordMap);
        Set<Long> unitIdSet = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex,after put: {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndex, after delete: {}", unitKeywordMap);
    }

    public boolean match(Long unitId, List<String> keywords) {
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {
            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }
        return false;
    }
}
