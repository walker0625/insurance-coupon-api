package com.backend.coupon.dto.res;

import java.util.UUID;

import com.backend.coupon.enums.LogType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponLogRes {

    Long couponLogSeq;

    String couponCode;

    UUID personId;

    LogType logType;

    String logDesc;

}
