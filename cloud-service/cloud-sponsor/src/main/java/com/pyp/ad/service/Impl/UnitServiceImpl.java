package com.pyp.ad.service.Impl;

import com.pyp.ad.constant.Constants;
import com.pyp.ad.entity.Plan;
import com.pyp.ad.entity.Unit;
import com.pyp.ad.entity.unit_condition.CreativeUnit;
import com.pyp.ad.entity.unit_condition.UnitDistrict;
import com.pyp.ad.entity.unit_condition.UnitIt;
import com.pyp.ad.entity.unit_condition.UnitKeyword;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.repository.CreativeRepository;
import com.pyp.ad.repository.PlanRepository;
import com.pyp.ad.repository.UnitRepository;
import com.pyp.ad.repository.unit_condition.CreativeUnitRepository;
import com.pyp.ad.repository.unit_condition.UnitDistrictRepository;
import com.pyp.ad.repository.unit_condition.UnitItRepository;
import com.pyp.ad.repository.unit_condition.UnitKeywordRepository;
import com.pyp.ad.service.IUnitService;
import com.pyp.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 21:53
 * @modifier:
 * @version: V1.0
 */
@Service
public class UnitServiceImpl implements IUnitService {

    private final UnitRepository unitRepository;

    private final PlanRepository planRepository;
    private final UnitKeywordRepository unitKeywordRepository;
    private final UnitItRepository unitItRepository;
    private final UnitDistrictRepository unitDistrictRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository,
                           PlanRepository planRepository,
                           UnitKeywordRepository unitKeywordRepository,
                           UnitItRepository unitItRepository,
                           UnitDistrictRepository unitDistrictRepository,
                           CreativeRepository creativeRepository,
                           CreativeUnitRepository creativeUnitRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    @Transactional
    public UnitResponse createUnit(UnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<Plan> optionalPlan = planRepository.findById(request.getPlanId());
        if (!optionalPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        Unit oldUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        Unit newUnit = unitRepository.save(new Unit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));
        return new UnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    @Transactional
    public UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(UnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<UnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new UnitKeyword(i.getUnitId(), i.getUnitKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(UnitKeyword::getId)
                    .collect(Collectors.toList());
        }
        return new UnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public UnitItResponse createUnitIt(UnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(UnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<UnitIt> unitIts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitIts())) {
            request.getUnitIts().forEach(i -> unitIts.add(
                    new UnitIt(i.getUnitId(), i.getItTag())
            ));
            ids = unitItRepository.saveAll(unitIts).stream()
                    .map(UnitIt::getId)
                    .collect(Collectors.toList());
        }
        return new UnitItResponse(ids);
    }

    @Override
    @Transactional
    public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream().map(UnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<UnitDistrict> unitDistricts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
            request.getUnitDistricts().forEach(i -> unitDistricts.add(
                    new UnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
            ));
            ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                    .map(UnitDistrict::getId)
                    .collect(Collectors.toList());
        }
        return new UnitDistrictResponse(ids);
    }

    @Override
    @Transactional
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        if (!(isRelatedUnitExist(unitIds)) || !(isRelatedCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        List<Long> ids = Collections.emptyList();
        if (CollectionUtils.isEmpty(request.getUnitItems())) {
            request.getUnitItems().forEach(i -> creativeUnits.add(
                    new CreativeUnit(i.getCreativeId(), i.getUnitId())
            ));
            ids = creativeUnitRepository.saveAll(creativeUnits).stream()
                    .map(CreativeUnit::getId).collect(Collectors.toList());
        }
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        //判断传进来的UnitId是否都存在
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        //判断传进来的creativeId是否都存在
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
