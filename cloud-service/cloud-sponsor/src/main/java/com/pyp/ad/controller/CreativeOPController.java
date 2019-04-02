package com.pyp.ad.controller;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.service.ICreativeService;
import com.pyp.ad.vo.CreativeRequest;
import com.pyp.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 23:51
 * @modifier:
 * @version: V1.0
 */
@RestController
@Slf4j
public class CreativeOPController {
    private final ICreativeService iCreativeService;

    @Autowired
    public CreativeOPController(ICreativeService iCreativeService) {
        this.iCreativeService = iCreativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("sponsor: createCreative ->{}", JSON.toJSON(request));
        return iCreativeService.createCreative(request);
    }
}
