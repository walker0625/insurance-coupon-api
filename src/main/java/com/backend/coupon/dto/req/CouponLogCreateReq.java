package com.backend.coupon.dto.req;

import java.util.UUID;

import com.backend.coupon.enums.LogType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponLogCreateReq {

    String couponCode;

    UUID personId;

    LogType logType; // ISSUED(발급자), USED(사용자) 구분

    String logDesc;

}