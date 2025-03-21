package com.backend.coupon.service;

import com.backend.coupon.dto.req.CouponLogCreateReq;
import com.backend.coupon.dto.res.CouponLogRes;
import com.backend.coupon.dto.res.PersonalCouponRes;
import com.backend.coupon.entity.CouponLog;
import com.backend.coupon.entity.Person;
import com.backend.coupon.entity.PersonalCoupon;
import com.backend.coupon.mapper.CouponLogMapper;
import com.backend.coupon.mapper.PersonalCouponMapper;
import com.backend.coupon.repository.CouponLogRepository;
import com.backend.coupon.repository.PersonRepository;
import com.backend.coupon.repository.PersonalCouponRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponStatusService {

    @Autowired
    PersonalCouponRepository personalCouponRepository;

    @Autowired
    CouponLogRepository couponLogRepository;

    @Autowired
    PersonRepository personRepository;

    public List<PersonalCouponRes> getPersonalCoupons(UUID personId) {

        List<PersonalCoupon> coupons = personalCouponRepository.findByPersonPersonId(personId);

        return coupons.stream()
            .map(PersonalCouponMapper::toRes)
            .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public CouponLogRes createCouponLog(CouponLogCreateReq req) {

        Person person = personRepository.findById(req.getPersonId())
                                        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        CouponLog couponLog = CouponLog.create(req.getCouponCode(), person, req.getLogType(), req.getLogDesc());
        CouponLog saved = couponLogRepository.save(couponLog);

        return CouponLogMapper.toRes(saved);
    }

    public List<CouponLogRes> getAllCouponLogs() {

        List<CouponLog> logs = couponLogRepository.findAll();

        return logs.stream()
            .map(CouponLogMapper::toRes)
            .collect(Collectors.toList());
    }

}