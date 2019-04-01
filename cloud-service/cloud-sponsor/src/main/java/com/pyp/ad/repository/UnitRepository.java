package com.pyp.ad.repository;

import com.pyp.ad.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 23:36
 * @modifier:
 * @version: V1.0
 */
public interface UnitRepository extends JpaRepository<Unit, Long> {
    /**
     * 根据推广单元id和单元名称查找
     *
     * @param planId
     * @param planName
     * @return
     */
    Unit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据单元状态批量查找
     * @param unitStatus
     * @return
     */
    List<Unit> findAllByUnitStatus(Integer unitStatus);
}
