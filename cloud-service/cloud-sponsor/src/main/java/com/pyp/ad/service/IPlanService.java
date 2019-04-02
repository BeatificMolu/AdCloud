package com.pyp.ad.service;

import com.pyp.ad.entity.Plan;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.PlanGetRequest;
import com.pyp.ad.vo.PlanRequest;
import com.pyp.ad.vo.PlanResponse;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:31
 * @modifier:
 * @version: V1.0
 */
public interface IPlanService {
    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    PlanResponse createPlan(PlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<Plan> getPlanByIds(PlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    PlanResponse updatePlan(PlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request
     * @throws AdException
     */
    void deletePlan(PlanRequest request) throws AdException;
}
