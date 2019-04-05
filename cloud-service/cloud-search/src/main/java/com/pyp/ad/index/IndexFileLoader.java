package com.pyp.ad.index;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.dump.DConstant;
import com.pyp.ad.dump.table.*;
import com.pyp.ad.handler.LevelDataHandler;
import com.pyp.ad.index.creativeunit.CreativeUnitObject;
import com.pyp.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/5 22:36
 * @modifier:
 * @version: V1.0
 */
@Component
@DependsOn("dataTable")
@Slf4j
public class IndexFileLoader {
    @PostConstruct
    public void init() {
        List<String> planStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.PLAN));
        planStrings.forEach(p -> LevelDataHandler.handleLevel2(JSON.parseObject(p, PlanTable.class), OpType.ADD));

        List<String> creativeStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.CREATIVE));
        creativeStrings.forEach(p -> LevelDataHandler.handleLevel2(JSON.parseObject(p, CreativeTable.class), OpType.ADD));

        List<String> unitStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT));
        unitStrings.forEach(p -> LevelDataHandler.handleLevel3(JSON.parseObject(p, UnitTable.class), OpType.ADD));

        List<String> creativeUnitStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.CREATIVE_UNIT));
        creativeUnitStrings.forEach(p -> LevelDataHandler.handleLevel3(JSON.parseObject(p, CreativeUnitTable.class), OpType.ADD));

        List<String> unitItStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_IT));
        unitItStrings.forEach(p -> LevelDataHandler.handleLevel4(JSON.parseObject(p, UnitItTable.class), OpType.ADD));

        List<String> unitDistrictStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_DISTRICT));
        unitDistrictStrings.forEach(p -> LevelDataHandler.handleLevel4(JSON.parseObject(p, UnitDistrictTable.class), OpType.ADD));

        List<String> unitKeywordStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.UNIT_KEYWORD));
        unitKeywordStrings.forEach(p -> LevelDataHandler.handleLevel4(JSON.parseObject(p, UnitKeywordTable.class), OpType.ADD));

    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
