package com.pyp.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pyp.ad.index.CommonStatus;
import com.pyp.ad.index.DataTable;
import com.pyp.ad.index.creative.CreativeIndex;
import com.pyp.ad.index.creative.CreativeObject;
import com.pyp.ad.index.creativeunit.CreativeUnitIndex;
import com.pyp.ad.index.district.UnitDistrictIndex;
import com.pyp.ad.index.interest.UnitItIndex;
import com.pyp.ad.index.keyword.UnitKeywordIndex;
import com.pyp.ad.index.unit.UnitIndex;
import com.pyp.ad.index.unit.UnitObject;
import com.pyp.ad.search.ISearch;
import com.pyp.ad.search.vo.Feature.DistrictFeature;
import com.pyp.ad.search.vo.Feature.FeatureRelation;
import com.pyp.ad.search.vo.Feature.ItFeature;
import com.pyp.ad.search.vo.Feature.KeywordFeature;
import com.pyp.ad.search.vo.SearchRequest;
import com.pyp.ad.search.vo.SearchResponse;
import com.pyp.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/10 21:49
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {
    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    @Override
    //@HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();
        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();
        for (AdSlot slot : adSlots) {
            Set<Long> targetUnitIdSet;
            //根据流量类型，获取初始Unit
            Set<Long> match = DataTable.of(UnitIndex.class).match(slot.getPositionType());
            if (relation.equals(FeatureRelation.AND)) {
                filterKeywordFeature(match, keywordFeature);
                filterDistrictFeature(match, districtFeature);
                filterItTagFeature(match, itFeature);
                targetUnitIdSet = match;
            } else {
                targetUnitIdSet = getOrRelationUnitIds(match, keywordFeature, itFeature, districtFeature);
            }
            List<UnitObject> unitObjects = DataTable.of(UnitIndex.class).fetch(targetUnitIdSet);
            filterUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);
            //实现adSlot对creativeObject的过滤，adSlot里面有宽高的限制
            filterCreativeByAdSlot(creativeObjects, slot.getWidth(), slot.getHeight(), slot.getType());
            adSlot2Ads.put(slot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }
        log.info("fetch ads: {}-{}", JSON.toJSON(relation), JSON.toJSON(response));
        return response;
    }

    private Set<Long> getOrRelationUnitIds(Set<Long> unitIds, KeywordFeature keywordFeature, ItFeature itFeature, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIds = new HashSet<>(unitIds);
        Set<Long> itUnitIds = new HashSet<>(unitIds);
        Set<Long> districtUnitIds = new HashSet<>(unitIds);
        filterKeywordFeature(keywordUnitIds, keywordFeature);
        filterDistrictFeature(districtUnitIds, districtFeature);
        filterItTagFeature(itUnitIds, itFeature);
        return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keywordUnitIds, itUnitIds), districtUnitIds));
    }

    private void filterKeywordFeature(Collection<Long> unitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(unitIds,
                    unitId ->
                            DataTable.of(UnitKeywordIndex.class)
                                    .match(unitId, keywordFeature.getKeywords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> unitIds, DistrictFeature feature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(feature.getDistricts())) {
            CollectionUtils.filter(unitIds, unitId -> DataTable.of(UnitDistrictIndex.class).match(feature.getDistricts(), unitId));
        }
    }

    private void filterItTagFeature(Collection<Long> unitIds, ItFeature feature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(feature.getTis())) {
            CollectionUtils.filter(unitIds, unitId -> DataTable.of(UnitItIndex.class).match(unitId, feature.getTis()));
        }
    }

    private void filterUnitAndPlanStatus(List<UnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(unitObjects, unitObject -> unitObject.getUnitStatus().equals(status.getStatus()) && unitObject.getPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    private void filterCreativeByAdSlot(List<CreativeObject> objects, Integer width, Integer height, List<Integer> types) {
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }
        CollectionUtils.filter(objects, object ->
                object.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && object.getWidth().equals(width)
                        && object.getHeight().equals(height)
                        && types.contains(object.getType())
        );
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }
        CreativeObject randomObject = objects.get(Math.abs(new Random().nextInt()) % objects.size());
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
