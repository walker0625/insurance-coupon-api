package com.backend.coupon.dto.req;

import lombok.*;
import lombok.experimental.FieldDefaults;

import com.backend.coupon.enums.CouponPublishType;
import com.backend.coupon.enums.CouponType;
import com.backend.coupon.enums.PurposeType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoUpdateReq {

    CouponType couponType;

    CouponPublishType couponPublishType;

    PurposeType purposeType;

    Double purposeValue;

    Integer limitCount;

}