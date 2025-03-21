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
@Table(name = "personal_coupon")
public class PersonalCoupon {

    @Builder.Default
    @Id
    @Column(name = "person_coupon_id")
    UUID personCouponId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    Person person;

    @Column(name = "coupon_code")
    String couponCode;

    @Column(name = "use_at")
    LocalDateTime useAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_subscription_details_id")
    InsuranceContract insuranceContract;

    @Column(name = "use_data")
    String useData;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false) // 발행 시점 추가
    LocalDateTime createdAt = LocalDateTime.now();

    public static PersonalCoupon create(Person person, String couponCode) {
        return PersonalCoupon.builder()
            .person(person)
            .couponCode(couponCode)
            .build();
    }

    public void useCoupon(InsuranceContract contract, String useData) {
        this.useAt = LocalDateTime.now();
        this.insuranceContract = contract;
        this.useData = useData;
    }

}