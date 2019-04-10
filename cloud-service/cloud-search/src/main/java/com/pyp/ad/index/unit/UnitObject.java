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

    /**
     * 判读流量类型
     *
     * @param positionType
     * @return
     */
    private static boolean isKaiPing(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.KAIPING) > 0;
    }

    private static boolean isTiePian(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }

    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTiePianPause(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTiePianPost(int positionType) {
        return (positionType & UnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    public static boolean isAdSlotTypeOK(int adSlodType, int positionType) {
        switch (adSlodType) {
            case UnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case UnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiePian(positionType);
            default:
                return false;

        }
    }

}
