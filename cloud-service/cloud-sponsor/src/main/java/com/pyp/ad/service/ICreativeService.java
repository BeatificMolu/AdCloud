package com.pyp.ad.service;

import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.CreateUserRequest;
import com.pyp.ad.vo.CreateUserResponse;
import com.pyp.ad.vo.CreativeRequest;
import com.pyp.ad.vo.CreativeResponse;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 22:52
 * @modifier:
 * @version: V1.0
 */
public interface ICreativeService {
    /**
     * 创建 创意
     *
     * @param request
     * @return
     */
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
