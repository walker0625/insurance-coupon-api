package com.backend.coupon.controller;

import com.backend.coupon.dto.req.CouponConditionCreateReq;
import com.backend.coupon.dto.req.CouponConditionUpdateReq;
import com.backend.coupon.dto.res.CouponConditionRes;
import com.backend.coupon.service.CouponConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "쿠폰 조건 관리")
@RequestMapping("/api/coupons/condition")
public class CouponConditionController {

    @Autowired
    CouponConditionService conditionService;

    @Operation(summary = "쿠폰 조건 등록")
    @PostMapping
    public ResponseEntity<CouponConditionRes> createCondition(@RequestBody CouponConditionCreateReq req) {

        return ResponseEntity.ok(conditionService.createCondition(req));
    }

    @Operation(summary = "쿠폰 조건 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCondition(@PathVariable Long id, @RequestBody CouponConditionUpdateReq req) {

        conditionService.updateCondition(id, req);

        return ResponseEntity.noContent().build();
    }

}