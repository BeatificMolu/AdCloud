package com.pyp.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:31
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequest {
    private Long id;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {
        return userId != null && StringUtils.isNotEmpty(planName) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate);
    }

    public boolean updateValidate() {
        return id != null && userId != null;
    }

    public boolean deleteValidate() {
        return id != null && userId != null;
    }
}
