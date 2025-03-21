package com.backend.coupon.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCouponRes {

    UUID personCouponId;

    UUID personId;

    String couponCode;

    LocalDateTime useAt;

    String useData;

}