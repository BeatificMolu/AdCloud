package com.pyp.ad.controller;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.annotation.IgnoreResponseAdvice;
import com.pyp.ad.client.SponsorClient;
import com.pyp.ad.client.vo.Plan;
import com.pyp.ad.client.vo.PlanGetRequest;
import com.pyp.ad.search.ISearch;
import com.pyp.ad.search.vo.SearchRequest;
import com.pyp.ad.search.vo.SearchResponse;
import com.pyp.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 22:12
 * @modifier:
 * @version: V1.0
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public class SearchController {
    private final ISearch iSearch;
    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(ISearch iSearch, RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.iSearch = iSearch;
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }
    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        log.info("ad-search: fetchAds -> {}",JSON.toJSON(request));
        return iSearch.fetchAds(request);
    }
    @PostMapping("/getPlanByRibbon")
    @IgnoreResponseAdvice
    public CommonResponse<List<Plan>> getPlanByRibbon(@RequestBody PlanGetRequest request) {
        log.info("serach: getPlanByRibbon ->{}", JSON.toJSON(request));
        return restTemplate.postForEntity("http://pyp-ad-sponsor/pyp-sponsor/get/plan", request, CommonResponse.class).getBody();
    }

    @PostMapping(value = "/getPlans")
    @IgnoreResponseAdvice
    CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request) {
        log.info("serach: getPlans ->{}", JSON.toJSON(request));
        return sponsorClient.getPlans(request);
    }
}
