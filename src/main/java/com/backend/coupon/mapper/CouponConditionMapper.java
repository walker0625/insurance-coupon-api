package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.CouponConditionRes;
import com.backend.coupon.entity.CouponCondition;

public class CouponConditionMapper {

    public static CouponConditionRes toRes(CouponCondition condition) {
        return CouponConditionRes.builder()
            .couponConditionSeq(condition.getCouponConditionSeq())
            .couponInfoSeq(condition.getCouponInfo().getCouponInfoSeq())
            .conditionType(condition.getConditionType())
            .mainValue(condition.getMainValue())
            .subValue(condition.getSubValue())
            .conditionDesc(condition.getConditionDesc())
            .build();
    }

}