package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.CouponLogRes;
import com.backend.coupon.entity.CouponLog;

public class CouponLogMapper {

    public static CouponLogRes toRes(CouponLog couponLog) {
        return CouponLogRes.builder()
            .couponLogSeq(couponLog.getCouponLogSeq())
            .couponCode(couponLog.getCouponCode())
            .personId(couponLog.getPerson().getPersonId())
            .logType(couponLog.getLogType())
            .logDesc(couponLog.getLogDesc())
            .build();
    }

}
