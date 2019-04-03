package com.pyp.ad.client;

import com.pyp.ad.client.vo.Plan;
import com.pyp.ad.client.vo.PlanGetRequest;
import com.pyp.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 22:26
 * @modifier:
 * @version: V1.0
 */
@FeignClient(value = "pyp-ad-sponsor",fallback = SponsorClientHystrix.class)
public interface SponsorClient {
    @RequestMapping(value = "/pyp-sponsor/get/plan", method = RequestMethod.POST)
    CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request);
}
