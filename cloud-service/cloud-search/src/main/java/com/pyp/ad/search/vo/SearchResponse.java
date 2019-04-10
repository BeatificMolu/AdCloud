package com.pyp.ad.search.vo;

import com.pyp.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:48
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private Map<String, List<Creative>> adSlot2Ads=new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative {
        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;
        /**
         * 展示检测url
         */
        private List<String> showMonitorUrl = new ArrayList<>();
        /**
         * 点击检测url
         */
        private List<String> clickMonitorUrl = new ArrayList<>();
    }

    public static Creative convert(CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setHeight(object.getHeight());
        creative.setWidth(object.getWidth());
        creative.setAdUrl(object.getAdUrl());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
