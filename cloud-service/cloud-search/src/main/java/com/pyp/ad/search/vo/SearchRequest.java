package com.pyp.ad.search.vo;

import com.pyp.ad.search.vo.Feature.DistrictFeature;
import com.pyp.ad.search.vo.Feature.FeatureRelation;
import com.pyp.ad.search.vo.Feature.ItFeature;
import com.pyp.ad.search.vo.Feature.KeywordFeature;
import com.pyp.ad.search.vo.media.AdSlot;
import com.pyp.ad.search.vo.media.App;
import com.pyp.ad.search.vo.media.Device;
import com.pyp.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:29
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    /**
     * 媒体方的请求标识
     */
    private String mediaId;
    /**
     * 请求基本信息
     */
    private RequestInfo requestInfo;
    /**
     * 匹配信息
     */
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {
        private KeywordFeature keywordFeature;
        private ItFeature itFeature;
        private DistrictFeature districtFeature;
        private FeatureRelation relation=FeatureRelation.AND;
    }
}
