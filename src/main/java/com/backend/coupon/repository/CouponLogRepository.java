package com.backend.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.CouponLog;

public interface CouponLogRepository extends JpaRepository<CouponLog, Long> {
}
