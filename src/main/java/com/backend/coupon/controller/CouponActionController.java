package com.backend.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.coupon.dto.req.CouponIssueReq;
import com.backend.coupon.dto.req.CouponUseReq;
import com.backend.coupon.dto.res.CouponIssueRes;
import com.backend.coupon.service.CouponActionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "쿠폰 발급 및 사용")
@RequestMapping("/api/coupons/action")
public class CouponActionController {

    @Autowired
    CouponActionService couponActionService;

    @Operation(summary = "쿠폰 발급")
    @PostMapping("/issue")
    public ResponseEntity<CouponIssueRes> issueCoupon(@RequestBody CouponIssueReq couponIssueReq) {

        return ResponseEntity.ok().body(couponActionService.issueCoupon(couponIssueReq));
    }

    @Operation(summary = "쿠폰 사용")
    @PostMapping("/use")
    public ResponseEntity<Void> useCoupon(@RequestBody CouponUseReq couponUseReq) {

        couponActionService.useCoupon(couponUseReq);

        return ResponseEntity.noContent().build();
    }

}