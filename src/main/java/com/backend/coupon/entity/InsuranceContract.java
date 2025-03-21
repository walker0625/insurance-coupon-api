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
@Table(name = "insurance_contract")
public class InsuranceContract {

    @Builder.Default
    @Id
    @Column(name = "contract_id")
    UUID contractId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    InsuranceProduct product;

    @Column(name = "premium")
    Double premium;

    @Column(name = "contract_start_date")
    LocalDateTime contractStartDate;

    @Column(name = "contract_end_date")
    LocalDateTime contractEndDate;

    @Column(name = "status")
    String status;

    @Builder.Default
    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

    public static InsuranceContract create(Person person, InsuranceProduct product,
                                           LocalDateTime contractStartDate, LocalDateTime contractEndDate) {
        return InsuranceContract.builder()
            .person(person)
            .product(product)
            .premium(product.getPremium())
            .contractStartDate(contractStartDate)
            .contractEndDate(contractEndDate)
            .status("활성")
            .build();
    }

    public void applyDiscount(double discountAmount) {

        if (discountAmount < 0 || discountAmount > this.premium) {
            throw new IllegalArgumentException("할인 금액이 유효하지 않습니다: " + discountAmount);
        }

        this.premium -= discountAmount;
        this.updatedAt = LocalDateTime.now();
    }

}