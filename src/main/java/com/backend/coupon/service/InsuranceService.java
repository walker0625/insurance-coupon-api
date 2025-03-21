package com.backend.coupon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.coupon.dto.req.CouponUseReq;
import com.backend.coupon.dto.req.InsuranceContractCreateReq;
import com.backend.coupon.dto.req.InsuranceProductCreateReq;
import com.backend.coupon.dto.res.InsuranceContractRes;
import com.backend.coupon.dto.res.InsuranceProductRes;
import com.backend.coupon.entity.InsuranceContract;
import com.backend.coupon.entity.InsuranceProduct;
import com.backend.coupon.entity.Person;
import com.backend.coupon.mapper.InsuranceContractMapper;
import com.backend.coupon.mapper.InsuranceProductMapper;
import com.backend.coupon.repository.InsuranceContractRepository;
import com.backend.coupon.repository.InsuranceProductRepository;
import com.backend.coupon.repository.PersonRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InsuranceService {

    @Autowired
    CouponActionService couponActionService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    InsuranceProductRepository insuranceProductRepository;

    @Autowired
    InsuranceContractRepository insuranceContractRepository;

    @Transactional(rollbackFor = Exception.class)
    public InsuranceProductRes registerProduct(InsuranceProductCreateReq request) {

        InsuranceProduct product = InsuranceProduct.create(
            request.getProductName(),
            request.getPremium()
        );

        return InsuranceProductMapper.toResponse(insuranceProductRepository.save(product));
    }

    @Transactional(rollbackFor = Exception.class)
    public InsuranceContractRes createContract(InsuranceContractCreateReq req) {

        Person person = personRepository.findById(req.getPersonId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        InsuranceProduct product = insuranceProductRepository.findById(req.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("보험 상품을 찾을 수 없습니다."));

        InsuranceContract contract = InsuranceContract.create(
            person,
            product,
            req.getContractStartDate(),
            req.getContractEndDate()
        );

        // 쿠폰 적용 (선택적)
        if (req.getCouponCode() != null && !req.getCouponCode().isEmpty()) {
            couponActionService.useCoupon(new CouponUseReq(req.getCouponCode(), req.getPersonId(), contract.getContractId()));
        }

        return InsuranceContractMapper.toResponse(insuranceContractRepository.save(contract));
    }

}