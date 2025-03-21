package com.backend.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon_book")
public class CouponBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_book_seq")
    Long couponBookSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_info_seq")
    CouponInfo couponInfo;

    @Column(name = "coupon_code", unique = true)
    String couponCode;

    @Builder.Default
    @Column(name = "is_used")
    Boolean isUsed = false;

    @Column(name = "expire_at")
    LocalDateTime expireAt;

    public static CouponBook create(CouponInfo couponInfo, String couponCode, LocalDateTime expireAt) {
        return CouponBook.builder()
            .couponInfo(couponInfo)
            .couponCode(couponCode)
            .expireAt(expireAt)
            .build();
    }

    public void markAsUsed() {
        this.isUsed = true;
    }

}