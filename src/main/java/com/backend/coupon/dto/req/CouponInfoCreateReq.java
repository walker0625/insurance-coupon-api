package com.backend.coupon.dto.req;

import java.time.LocalDateTime;

import com.backend.coupon.enums.CouponBadgeType;
import com.backend.coupon.enums.CouponPublishType;
import com.backend.coupon.enums.CouponType;
import com.backend.coupon.enums.PurposeType;
import com.backend.coupon.enums.TargetType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoCreateReq {

    CouponType couponType;

    String couponName;

    TargetType targetType;

    CouponPublishType couponPublishType;

    PurposeType purposeType;

    Double purposeValue;

    CouponBadgeType couponBadgeType;

    String couponImageUrl;

    Integer limitCount;

    Boolean isAble;

    Boolean isDuplicate;

    LocalDateTime couponStartAt;

    LocalDateTime couponEndAt;

}