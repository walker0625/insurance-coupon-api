package com.backend.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.CouponBook;

public interface CouponBookRepository extends JpaRepository<CouponBook, Long> {

    Optional<CouponBook> findByCouponCode(String couponCode);

}
