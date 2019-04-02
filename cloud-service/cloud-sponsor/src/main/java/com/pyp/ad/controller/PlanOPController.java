package com.pyp.ad.controller;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.entity.Plan;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.service.IPlanService;
import com.pyp.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 23:35
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@RestController
public class PlanOPController {
    private final IPlanService iPlanService;

    @Autowired
    public PlanOPController(IPlanService iPlanService) {
        this.iPlanService = iPlanService;
    }

    @PostMapping("/create/plan")
    public PlanResponse createPlan(@RequestBody PlanRequest request) throws AdException {
        log.info("sponsor: createPlan ->{}", JSON.toJSON(request));
        return iPlanService.createPlan(request);
    }

    @PostMapping("/get/plan")
    public List<Plan> getPlanByIds(@RequestBody PlanGetRequest request) throws AdException {
        log.info("sponsor: getPlanByIds ->{}", JSON.toJSON(request));
        return iPlanService.getPlanByIds(request);
    }

    @PostMapping("/update/plan")
    public PlanResponse updatePlan(@RequestBody PlanRequest request) throws AdException {
        log.info("sponsor: updatePlan ->{}", JSON.toJSON(request));
        return iPlanService.updatePlan(request);
    }

    @PostMapping("/delete/plan")
    public void deletePlan(@RequestBody PlanRequest request) throws AdException {
        log.info("sponsor: deletePlan ->{}", JSON.toJSON(request));
        iPlanService.deletePlan(request);
    }
}
