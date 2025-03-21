package com.backend.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.coupon.dto.req.CouponInfoCreateReq;
import com.backend.coupon.dto.req.CouponInfoUpdateReq;
import com.backend.coupon.dto.res.CouponInfoCreateRes;
import com.backend.coupon.dto.res.CouponInfoRes;
import com.backend.coupon.service.CouponInfoService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "쿠폰 정보 관리")
@RequestMapping("/api/coupons/info")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @Operation(summary = "쿠폰 정보 등록")
    @PostMapping
    public ResponseEntity<CouponInfoCreateRes> createCouponInfo(@RequestBody CouponInfoCreateReq req) {

        return ResponseEntity.ok(couponInfoService.createCouponInfo(req));
    }

    @Operation(summary = "쿠폰 정보 단일 조회")
    @GetMapping("/{id}")
    public ResponseEntity<CouponInfoRes> getCouponInfoById(@PathVariable Long id) {

        return ResponseEntity.ok(couponInfoService.getCouponInfoById(id));
    }

    @Operation(summary = "쿠폰 정보 목록 조회")
    @GetMapping
    public ResponseEntity<List<CouponInfoRes>> getAllCouponInfos() {

        return ResponseEntity.ok(couponInfoService.getAllCouponInfos());
    }

    @Operation(summary = "쿠폰 정보 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCouponInfo(@PathVariable Long id, @RequestBody CouponInfoUpdateReq req) {

        couponInfoService.updateCouponInfo(id, req);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "쿠폰 정보 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponInfo(@PathVariable Long id) {

        couponInfoService.deleteCouponInfo(id);

        return ResponseEntity.noContent().build();
    }

}