package com.pyp.ad.service.Impl;

import com.pyp.ad.constant.CommonStatus;
import com.pyp.ad.constant.Constants;
import com.pyp.ad.entity.Plan;
import com.pyp.ad.entity.User;
import com.pyp.ad.exception.AdException;
import com.pyp.ad.repository.PlanRepository;
import com.pyp.ad.repository.UserRepository;
import com.pyp.ad.service.IPlanService;
import com.pyp.ad.utils.CommonUtils;
import com.pyp.ad.vo.PlanGetRequest;
import com.pyp.ad.vo.PlanRequest;
import com.pyp.ad.vo.PlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:42
 * @modifier:
 * @version: V1.0
 */
@Service
public class PlanServiceImpl implements IPlanService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(UserRepository userRepository,
                           PlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public PlanResponse createPlan(PlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //确保关联的用户是存在的
        Optional<User> user = userRepository.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        Plan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        Plan newPlan = planRepository.save(
                new Plan(request.getUserId(), request.getPlanName(),
                        CommonUtils.parseStringDate(request.getStartDate()),
                        CommonUtils.parseStringDate(request.getEndDate())));
        return new PlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    public List<Plan> getPlanByIds(PlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(PlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(plan==null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if(request.getPlanName()!=null){
            plan.setPlanName(request.getPlanName());
        }
        if(request.getStartDate()!=null){
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }
        if(request.getEndDate()!=null){
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());
        plan=planRepository.save(plan);
        return new PlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePlan(PlanRequest request) throws AdException {
        if(!request.deleteValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(plan==null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        plan=planRepository.save(plan);
    }
}
