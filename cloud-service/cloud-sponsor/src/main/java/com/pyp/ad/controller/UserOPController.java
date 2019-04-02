package com.pyp.ad.controller;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.service.IUserService;
import com.pyp.ad.vo.CreateUserRequest;
import com.pyp.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 23:30
 * @modifier:
 * @version: V1.0
 */

@Slf4j
@RestController
public class UserOPController {
    @Autowired
    private IUserService iUserService;

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("sponsor: createUser ->{}", JSON.toJSON(request));
        return iUserService.createUser(request);
    }
}
