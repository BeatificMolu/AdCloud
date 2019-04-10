package com.pyp.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:36
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {
    private Float latitude;
    private Float longitude;
    private String province;
    private String city;
}
