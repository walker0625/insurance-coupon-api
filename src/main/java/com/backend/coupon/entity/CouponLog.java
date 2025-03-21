package com.backend.coupon.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.backend.coupon.enums.LogType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon_log")
public class CouponLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_log_seq")
    Long couponLogSeq;

    @Column(name = "coupon_code")
    String couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    LogType logType;

    @Column(name = "log_desc")
    String logDesc;

    public static CouponLog create(String couponCode, Person person, LogType logType, String logDesc) {
        return CouponLog.builder()
            .couponCode(couponCode)
            .person(person)
            .logType(logType)
            .logDesc(logDesc)
            .build();
    }

}