package com.backend.coupon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.coupon.dto.req.CouponIssueReq;
import com.backend.coupon.dto.req.CouponUseReq;
import com.backend.coupon.dto.res.CouponIssueRes;
import com.backend.coupon.entity.CouponBook;
import com.backend.coupon.entity.CouponInfo;
import com.backend.coupon.entity.CouponLog;
import com.backend.coupon.entity.InsuranceContract;
import com.backend.coupon.entity.Person;
import com.backend.coupon.entity.PersonalCoupon;
import com.backend.coupon.enums.LogType;
import com.backend.coupon.repository.CouponBookRepository;
import com.backend.coupon.repository.CouponInfoRepository;
import com.backend.coupon.repository.CouponLogRepository;
import com.backend.coupon.repository.InsuranceContractRepository;
import com.backend.coupon.repository.PersonRepository;
import com.backend.coupon.repository.PersonalCouponRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponActionService {

    @Autowired
    CouponInfoRepository couponInfoRepository;

    @Autowired
    CouponBookRepository couponBookRepository;

    @Autowired
    PersonalCouponRepository personalCouponRepository;

    @Autowired
    CouponLogRepository couponLogRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    InsuranceContractRepository insuranceContractRepository;

    @Transactional(rollbackFor = Exception.class)
    public CouponIssueRes issueCoupon(CouponIssueReq req) {

        CouponInfo couponInfo = couponInfoRepository.findById(req.getCouponInfoId())
                                                    .orElseThrow(() -> new IllegalArgumentException("쿠폰 정보를 찾을 수 없습니다."));

        Person person = personRepository.findById(req.getPersonId())
                                        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!couponInfo.getIsAble() || couponInfo.getPressCount() >= couponInfo.getLimitCount()) {
            throw new IllegalStateException("발급 가능한 쿠폰이 아닙니다.");
        }

        String couponCode = generateCouponCode();

        couponBookRepository.save(CouponBook.create(couponInfo, couponCode, couponInfo.getCouponEndAt()));

        personalCouponRepository.save(PersonalCoupon.create(person, couponCode));

        couponLogRepository.save(CouponLog.create(couponCode, person, LogType.PUBLISH, "쿠폰 발급: " + couponInfo.getCouponName()));

        couponInfo.incrementPressCount();

        return new CouponIssueRes(couponCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void useCoupon(CouponUseReq req) {

        String couponCode = req.getCouponCode();
        UUID personId = req.getPersonId();
        UUID contractId = req.getContractId();

        CouponBook couponBook = couponBookRepository.findByCouponCode(couponCode)
                                                    .orElseThrow(() -> new IllegalArgumentException("쿠폰 코드를 찾을 수 없습니다."));

        Person person = personRepository.findById(personId)
                                        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        InsuranceContract contract = insuranceContractRepository.findById(contractId)
                                                                .orElseThrow(() -> new IllegalArgumentException("계약 정보를 찾을 수 없습니다."));

        PersonalCoupon personalCoupon = personalCouponRepository.findByPersonPersonIdAndCouponCode(personId, couponCode);

        if (couponBook.getIsUsed() || couponBook.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("사용 불가능한 쿠폰입니다.");
        }

        // CouponCondition 검증
        CouponInfo couponInfo = couponBook.getCouponInfo();
        couponInfo.validateConditions(contract, personalCoupon);

        // 할인 금액 계산 및 적용
        double discountAmount = couponInfo.calculateDiscount(contract);
        contract.applyDiscount(discountAmount);

        // 쿠폰 사용 처리
        couponBook.markAsUsed();
        personalCoupon.useCoupon(contract, "보험 계약에 사용: 할인 " + discountAmount + "원 적용");
        couponInfo.incrementUseCount();

        // 로그 기록
        couponLogRepository.save(CouponLog.create(couponCode, person, LogType.USE,
                        "쿠폰 사용: " + couponInfo.getCouponName() + ", 할인 금액: " + discountAmount + "원"));
    }

    public String generateCouponCode() {
        return UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

}