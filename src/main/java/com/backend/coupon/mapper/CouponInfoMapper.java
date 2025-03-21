package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.CouponInfoCreateRes;
import com.backend.coupon.dto.res.CouponInfoRes;
import com.backend.coupon.entity.CouponInfo;

public class CouponInfoMapper {

    public static CouponInfoCreateRes toCreateRes(CouponInfo couponInfo) {
        return new CouponInfoCreateRes(couponInfo.getCouponInfoSeq());
    }

    public static CouponInfoRes toRes(CouponInfo couponInfo) {
        return CouponInfoRes.builder()
            .couponInfoSeq(couponInfo.getCouponInfoSeq())
            .couponType(couponInfo.getCouponType())
            .couponName(couponInfo.getCouponName())
            .targetType(couponInfo.getTargetType())
            .couponPublishType(couponInfo.getCouponPublishType())
            .purposeType(couponInfo.getPurposeType())
            .purposeValue(couponInfo.getPurposeValue())
            .couponBadgeType(couponInfo.getCouponBadgeType())
            .couponImageUrl(couponInfo.getCouponImageUrl())
            .pressCount(couponInfo.getPressCount())
            .useCount(couponInfo.getUseCount())
            .limitCount(couponInfo.getLimitCount())
            .isAble(couponInfo.getIsAble())
            .isDuplicate(couponInfo.getIsDuplicate())
            .couponStartAt(couponInfo.getCouponStartAt())
            .couponEndAt(couponInfo.getCouponEndAt())
            .build();
    }

}