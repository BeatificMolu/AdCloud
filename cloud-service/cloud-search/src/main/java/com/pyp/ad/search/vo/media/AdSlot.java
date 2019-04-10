package com.pyp.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:32
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlot {
    /**
     * 广告位的编码
     */
    private String adSlotCode;
    /**
     * 流量类型
     */
    private Integer positionType;
    private Integer height;
    private Integer width;
    /**
     * 广告物料的类型
     */
    private List<Integer> type;
    /**
     * 最低出价
     */
    private Integer minCpm;
}
