package com.backend.coupon.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.InsuranceProduct;

public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, UUID> {
}
