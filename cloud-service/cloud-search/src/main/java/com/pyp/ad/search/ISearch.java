package com.pyp.ad.search;

import com.pyp.ad.search.vo.SearchRequest;
import com.pyp.ad.search.vo.SearchResponse;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:29
 * @modifier:
 * @version: V1.0
 */
public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
