package com.pyp.ad.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pyp.ad.Application;
import com.pyp.ad.constant.CommonStatus;
import com.pyp.ad.dump.DConstant;
import com.pyp.ad.dump.table.*;
import com.pyp.ad.entity.Creative;
import com.pyp.ad.entity.Plan;
import com.pyp.ad.entity.Unit;
import com.pyp.ad.entity.unit_condition.CreativeUnit;
import com.pyp.ad.entity.unit_condition.UnitDistrict;
import com.pyp.ad.entity.unit_condition.UnitIt;
import com.pyp.ad.entity.unit_condition.UnitKeyword;
import com.pyp.ad.repository.CreativeRepository;
import com.pyp.ad.repository.PlanRepository;
import com.pyp.ad.repository.UnitRepository;
import com.pyp.ad.repository.unit_condition.CreativeUnitRepository;
import com.pyp.ad.repository.unit_condition.UnitDistrictRepository;
import com.pyp.ad.repository.unit_condition.UnitItRepository;
import com.pyp.ad.repository.unit_condition.UnitKeywordRepository;
import com.pyp.ad.vo.UnitItRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/5 10:33
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private UnitDistrictRepository unitDistrictRepository;
    @Autowired
    private UnitKeywordRepository unitKeywordRepository;
    @Autowired
    private UnitItRepository unitItRepository;

    @Test
    public void dumpTableDump() {
        dumpPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.PLAN));
        dumpUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT));
        dumpCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.CREATIVE));
        dumpCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.CREATIVE_UNIT));
        dumpUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_IT));
        dumpUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_KEYWORD));
        dumpUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_DISTRICT));
    }

    private void dumpPlanTable(String fileName) {
        List<Plan> plans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(plans)) {
            return;
        }
        List<PlanTable> planTables = new ArrayList<>();
        plans.forEach(p -> planTables.add(
                new PlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (PlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dump error" + e.getMessage());
        }
    }

    private void dumpUnitTable(String fileName) {
        List<Unit> units = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(units)) {
            return;
        }
        List<UnitTable> unitTables = new ArrayList<>();
        units.forEach(u -> unitTables.add(
                new UnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("unitTable dump error" + e.getMessage());
        }
    }

    private void dumpCreativeTable(String fileName) {
        List<Creative> creativeList = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creativeList)) {
            return;
        }
        List<CreativeTable> creativeTables = new ArrayList<>();
        creativeList.forEach(c -> creativeTables.add(
                new CreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(), c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("creative dump error" + e.getMessage());
        }
    }

    private void dumpUnitItTable(String fileName) {
        List<UnitIt> unitIts = unitItRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }
        List<UnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(u -> unitItTables.add(
                new UnitItTable(u.getUnitId(), u.getItTag())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("unitItTable dump error:" + e.getMessage());
        }
    }

    private void dumpCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new CreativeUnitTable(c.getUnitId(), c.getId())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("creativeUnitTable dump error:" + e.getMessage());
        }
    }

    private void dumpUnitKeywordTable(String fileName) {
        List<UnitKeyword> unitKeywords = unitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }
        List<UnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(u -> unitKeywordTables.add(
                new UnitKeywordTable(u.getUnitId(), u.getKeyword())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {

        }
    }

    private void dumpUnitDistrictTable(String fileName) {
        List<UnitDistrict> unitDistricts = unitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }
        List<UnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(u -> unitDistrictTables.add(
                new UnitDistrictTable(u.getUnitId(), u.getProvince(), u.getCity())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("unitDistrictTable dump error:" + e.getMessage());
        }
    }

}
