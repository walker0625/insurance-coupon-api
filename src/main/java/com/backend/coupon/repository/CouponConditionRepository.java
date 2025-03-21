package com.backend.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.CouponCondition;

public interface CouponConditionRepository extends JpaRepository<CouponCondition, Long> {
}
