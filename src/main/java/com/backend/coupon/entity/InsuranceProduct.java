package com.backend.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "insurance_product")
public class InsuranceProduct {

    @Builder.Default
    @Id
    @Column(name = "product_id")
    UUID productId = UUID.randomUUID();

    @Column(name = "product_name")
    String productName;

    @Column(name = "premium")
    Double premium;

    @Builder.Default
    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

    public static InsuranceProduct create(String productName, Double premium) {
        return InsuranceProduct.builder()
            .productName(productName)
            .premium(premium)
            .build();
    }

}