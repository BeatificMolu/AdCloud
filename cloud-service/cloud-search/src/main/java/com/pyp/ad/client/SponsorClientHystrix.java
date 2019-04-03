package com.pyp.ad.client;

import com.pyp.ad.client.vo.Plan;
import com.pyp.ad.client.vo.PlanGetRequest;
import com.pyp.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 22:34
 * @modifier:
 * @version: V1.0
 */
@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<Plan>> getPlans(PlanGetRequest request) {
        return new CommonResponse<>(-1, "pyp-ad-sponsor error");
    }
}
