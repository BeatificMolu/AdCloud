package com.pyp.ad.search.vo.Feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:43
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictFeature {
    private List<ProvinceAndCity> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProvinceAndCity {
        private String province;
        private String city;
    }

}
