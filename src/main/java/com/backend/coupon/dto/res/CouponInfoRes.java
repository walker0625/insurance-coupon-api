package com.backend.coupon.dto.res;

import com.backend.coupon.enums.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoRes {

    Long couponInfoSeq;

    CouponType couponType;

    String couponName;

    TargetType targetType;

    CouponPublishType couponPublishType;

    PurposeType purposeType;

    Double purposeValue;

    CouponBadgeType couponBadgeType;

    String couponImageUrl;

    Integer pressCount;

    Integer useCount;

    Integer limitCount;

    Boolean isAble;

    Boolean isDuplicate;

    LocalDateTime couponStartAt;

    LocalDateTime couponEndAt;

}