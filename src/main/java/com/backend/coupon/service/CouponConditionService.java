package com.backend.coupon.service;

import com.backend.coupon.dto.req.CouponConditionCreateReq;
import com.backend.coupon.dto.req.CouponConditionUpdateReq;
import com.backend.coupon.dto.res.CouponConditionRes;
import com.backend.coupon.entity.CouponCondition;
import com.backend.coupon.entity.CouponInfo;
import com.backend.coupon.mapper.CouponConditionMapper;
import com.backend.coupon.repository.CouponConditionRepository;
import com.backend.coupon.repository.CouponInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponConditionService {

    @Autowired
    CouponConditionRepository conditionRepository;

    @Autowired
    CouponInfoRepository couponInfoRepository;

    @Transactional(rollbackFor = Exception.class)
    public CouponConditionRes createCondition(CouponConditionCreateReq req) {

        CouponInfo couponInfo = couponInfoRepository.findById(req.getCouponInfoSeq())
                                                    .orElseThrow(() -> new IllegalArgumentException("쿠폰 정보를 찾을 수 없습니다."));

        CouponCondition couponCondition = CouponCondition.create(couponInfo, req.getConditionType(), req.getMainValue(),
                                                                 req.getSubValue(), req.getConditionDesc());

        CouponCondition saved = conditionRepository.save(couponCondition);

        return CouponConditionMapper.toRes(saved);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCondition(Long conditionId, CouponConditionUpdateReq req) {

        CouponCondition condition = conditionRepository.findById(conditionId)
                                                       .orElseThrow(() -> new IllegalArgumentException("쿠폰 조건을 찾을 수 없습니다."));

        condition.updateCondition(req.getConditionType(), req.getMainValue(), req.getSubValue(), req.getConditionDesc());
    }

}