package com.backend.coupon.dto.res;

import com.backend.coupon.enums.ConditionType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponConditionRes {

    Long couponConditionSeq;

    Long couponInfoSeq;

    ConditionType conditionType;

    String mainValue;

    String subValue;

    String conditionDesc;

}