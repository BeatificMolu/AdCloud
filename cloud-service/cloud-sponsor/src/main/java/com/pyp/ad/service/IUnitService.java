package com.pyp.ad.service;

import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.*;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 21:50
 * @modifier:
 * @version: V1.0
 */
public interface IUnitService {
    /**
     * 创建推广单元
     *
     * @param request
     * @return
     * @throws AdException
     */
    UnitResponse createUnit(UnitRequest request) throws AdException;

    /**
     * 创建推广单元关键字限制
     *
     * @param request
     * @return
     * @throws AdException
     */
    UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException;

    /**
     * 添加推广单元兴趣的限制
     *
     * @param request
     * @return
     * @throws AdException
     */
    UnitItResponse createUnitIt(UnitItRequest request) throws AdException;

    /**
     * 添加推广单元地域限制
     *
     * @param request
     * @return
     * @throws AdException
     */
    UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException;

    /**
     * 添加推广单元与推广创意的关联关系
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
