package com.pyp.ad.index.unit;

import com.pyp.ad.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 23:09
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitObject {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
    private PlanObject planObject;

    public void update(UnitObject newObject) {
        if (null != newObject.getUnitId()) {
            this.unitId = newObject.getUnitId();
        }
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }
        if (null != newObject.getPositionType()) {
            this.positionType = newObject.getPositionType();
        }
        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (null != newObject.getPlanObject()) {
            this.planObject = newObject.getPlanObject();
        }
    }
}
