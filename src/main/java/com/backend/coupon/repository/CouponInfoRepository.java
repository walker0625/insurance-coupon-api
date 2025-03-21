package com.backend.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.CouponInfo;

public interface CouponInfoRepository extends JpaRepository<CouponInfo, Long> {
}
