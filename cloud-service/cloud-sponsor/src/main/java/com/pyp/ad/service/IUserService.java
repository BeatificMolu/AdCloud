package com.pyp.ad.service;

import com.pyp.ad.exception.AdException;
import com.pyp.ad.vo.CreateUserRequest;
import com.pyp.ad.vo.CreateUserResponse;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:11
 * @modifier:
 * @version: V1.0
 */
public interface IUserService {
    /**
     * 创建用户
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
