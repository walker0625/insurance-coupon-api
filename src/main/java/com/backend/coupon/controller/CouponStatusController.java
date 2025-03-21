package com.backend.coupon.controller;

import com.backend.coupon.dto.req.CouponLogCreateReq;
import com.backend.coupon.dto.res.CouponLogRes;
import com.backend.coupon.dto.res.PersonalCouponRes;
import com.backend.coupon.service.CouponStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "쿠폰 사용 이력 및 보유 현황")
@RequestMapping("/api/coupons/status")
public class CouponStatusController {

    @Autowired
    CouponStatusService couponStatusService;

    @Operation(summary = "특정 사용자의 쿠폰 목록 조회")
    @GetMapping("/personal/{personId}")
    public ResponseEntity<List<PersonalCouponRes>> getPersonalCoupons(@PathVariable String personId) {

        return ResponseEntity.ok(couponStatusService.getPersonalCoupons(UUID.fromString(personId)));
    }

    @Operation(summary = "쿠폰 이력 등록")
    @PostMapping("/log")
    public ResponseEntity<CouponLogRes> createCouponLog(@RequestBody CouponLogCreateReq req) {

        return ResponseEntity.ok(couponStatusService.createCouponLog(req));
    }

    @Operation(summary = "쿠폰 이력 목록 조회")
    @GetMapping("/log")
    public ResponseEntity<List<CouponLogRes>> getAllCouponLogs() {

        return ResponseEntity.ok(couponStatusService.getAllCouponLogs());
    }

}