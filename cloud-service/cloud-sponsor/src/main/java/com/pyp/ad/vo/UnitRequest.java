package com.pyp.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 21:50
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequest {
    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null && StringUtils.isNotEmpty(unitName) && positionType != null && budget != null;
    }
}
