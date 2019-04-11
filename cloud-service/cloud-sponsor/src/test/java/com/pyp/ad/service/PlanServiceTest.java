package com.pyp.ad.service;

import com.pyp.ad.Application;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.PlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/11 22:32
 * @modifier:
 * @version: V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlanServiceTest {
    @Autowired
    private IPlanService iPlanService;
    @Test
    public void testGetPlan() throws AdException{
        System.out.println(iPlanService.getPlanByIds(new PlanGetRequest(15L, Collections.singletonList(10L))));
    }
}
