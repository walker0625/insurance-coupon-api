package com.backend.coupon.dto.req;

import com.backend.coupon.enums.ConditionType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CouponConditionUpdateReq {

    ConditionType conditionType;

    String mainValue;

    String subValue;

    String conditionDesc;

}
