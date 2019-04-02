package com.pyp.ad.service.Impl;

import com.pyp.ad.constant.Constants;
import com.pyp.ad.entity.User;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.repository.UserRepository;
import com.pyp.ad.service.IUserService;
import com.pyp.ad.utils.CommonUtils;
import com.pyp.ad.vo.CreateUserRequest;
import com.pyp.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:17
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        //验证参数是否正确，为什么不从controller里判断
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //判断是否有重名的用户
        User oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        User newUser = userRepository.save(new User(request.getUsername(), CommonUtils.MD5(request.getUsername())));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
