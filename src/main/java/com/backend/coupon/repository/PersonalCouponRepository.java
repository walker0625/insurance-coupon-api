package com.backend.coupon.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.PersonalCoupon;

public interface PersonalCouponRepository extends JpaRepository<PersonalCoupon, UUID> {

    PersonalCoupon findByPersonPersonIdAndCouponCode(UUID personId, String couponCode);

    List<PersonalCoupon> findByPersonPersonId(UUID personId);

}
