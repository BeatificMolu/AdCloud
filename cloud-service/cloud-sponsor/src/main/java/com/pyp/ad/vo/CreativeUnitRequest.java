package com.pyp.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 23:14
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeUnitRequest {
    private List<CreativeUnitItem> unitItems;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreativeUnitItem{
        private Long creativeId;
        private Long unitId;
    }
}
