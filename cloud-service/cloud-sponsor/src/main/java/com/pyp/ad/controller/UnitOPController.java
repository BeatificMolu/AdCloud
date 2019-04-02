package com.pyp.ad.controller;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.entity.unit_condition.UnitKeyword;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.service.IUnitService;
import com.pyp.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 23:43
 * @modifier:
 * @version: V1.0
 */
@RestController
@Slf4j
public class UnitOPController {
    private final IUnitService iUnitService;

    @Autowired
    public UnitOPController(IUnitService iUnitService) {
        this.iUnitService = iUnitService;
    }

    @PostMapping("/create/unit")
    public UnitResponse createUnit(@RequestBody UnitRequest request) throws AdException {
        log.info("sponsor: createUnit ->{}", JSON.toJSON(request));
        return iUnitService.createUnit(request);
    }

    @PostMapping("/create/unitKeyword")
    public UnitKeywordResponse createUnitKeyword(@RequestBody UnitKeywordRequest request) throws AdException {
        log.info("sponsor: createUnitKeyword ->{}", JSON.toJSON(request));
        return iUnitService.createUnitKeyword(request);
    }

    @PostMapping("/create/unitIt")
    public UnitItResponse createUnitIt(@RequestBody UnitItRequest request) throws AdException {
        log.info("sponsor: createUnitIt ->{}", JSON.toJSON(request));
        return iUnitService.createUnitIt(request);
    }

    @PostMapping("/create/unitDistrict")
    public UnitDistrictResponse createUnitDistrict(@RequestBody UnitDistrictRequest request) throws AdException {
        log.info("sponsor: createUnitDistrict ->{}", JSON.toJSON(request));
        return iUnitService.createUnitDistrict(request);
    }

    @PostMapping("/create/createUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("sponsor: createUnit ->{}", JSON.toJSON(request));
        return iUnitService.createCreativeUnit(request);
    }
}
