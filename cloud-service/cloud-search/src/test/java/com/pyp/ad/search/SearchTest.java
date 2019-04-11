package com.pyp.ad.search;

import com.pyp.ad.Application;
import com.pyp.ad.search.vo.Feature.DistrictFeature;
import com.pyp.ad.search.vo.Feature.FeatureRelation;
import com.pyp.ad.search.vo.Feature.ItFeature;
import com.pyp.ad.search.vo.Feature.KeywordFeature;
import com.pyp.ad.search.vo.SearchRequest;
import com.pyp.ad.search.vo.media.AdSlot;
import com.pyp.ad.search.vo.media.App;
import com.pyp.ad.search.vo.media.Device;
import com.pyp.ad.search.vo.media.Geo;
import io.micrometer.core.instrument.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/11 22:42
 * @modifier:
 * @version: V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {
    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("imooc-ad");
        //第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot("ad-p30", 1, 1080, 720, Arrays.asList(1, 2), 1000)),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
    }

    private App buildExampleApp() {
        return new App("pyp", "pyp", "com.pyp", "MainActivity");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 112.85, (float) 120.1, "北京市", "朝阳人民群众");
    }

    private Device buildExampleDevice() {
        return new Device("华为", "06EE5C4F88EA", "127.0.0.1", "P30-PRO", "1120 2160", "1120 2160", "468416574");
    }

    private SearchRequest.FeatureInfo buildRequestFeatureExample(List<String> ks, List<String> its, List<DistrictFeature.ProvinceAndCity> pcs, FeatureRelation relation) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(ks),
                new ItFeature(its),
                new DistrictFeature(pcs),
                relation);
    }
}
