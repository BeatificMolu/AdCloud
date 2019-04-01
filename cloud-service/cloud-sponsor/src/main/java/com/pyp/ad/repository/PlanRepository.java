package com.pyp.ad.repository;

import com.pyp.ad.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 23:26
 * @modifier:
 * @version: V1.0
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
    /**
     * 根据id和userid查计划
     *
     * @param id
     * @param userId
     * @return
     */
    Plan findByIdAndUserId(Long id, Long userId);

    /**
     * 批量查找
     *
     * @param ids
     * @param userId
     * @return
     */
    List<Plan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据userid和计划名称查找
     *
     * @param userId
     * @param planName
     * @return
     */
    Plan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据状态批量查找
     *
     * @param status
     * @return
     */
    List<Plan> findAllByPlanStatus(Integer status);
}
