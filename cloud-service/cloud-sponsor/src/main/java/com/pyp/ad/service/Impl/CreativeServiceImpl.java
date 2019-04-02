package com.pyp.ad.service.Impl;

import com.pyp.ad.constant.Constants;
import com.pyp.ad.entity.Creative;
import com.pyp.ad.entity.User;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.repository.CreativeRepository;
import com.pyp.ad.repository.UserRepository;
import com.pyp.ad.service.ICreativeService;
import com.pyp.ad.vo.CreateUserResponse;
import com.pyp.ad.vo.CreativeRequest;
import com.pyp.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 22:53
 * @modifier:
 * @version: V1.0
 */
@Service
public class CreativeServiceImpl implements ICreativeService {
    private final CreativeRepository creativeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository,
                               UserRepository userRepository) {
        this.creativeRepository = creativeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        if (!request.creativeValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (!userOptional.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        Creative oldCreative = creativeRepository.findByNameAndUserId(request.getName(), request.getUserId());
        if (oldCreative != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_CREATIVE_ERROR);
        }
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
