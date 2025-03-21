package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.PersonalCouponRes;
import com.backend.coupon.entity.PersonalCoupon;

public class PersonalCouponMapper {

    public static PersonalCouponRes toRes(PersonalCoupon personalCoupon) {
        return PersonalCouponRes.builder()
            .personCouponId(personalCoupon.getPersonCouponId())
            .personId(personalCoupon.getPerson().getPersonId())
            .couponCode(personalCoupon.getCouponCode())
            .useAt(personalCoupon.getUseAt())
            .useData(personalCoupon.getUseData())
            .build();
    }

}