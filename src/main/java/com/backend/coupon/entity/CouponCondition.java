package com.backend.coupon.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.backend.coupon.enums.ConditionType;

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
@Table(name = "coupon_condition")
public class CouponCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_condition_seq")
    Long couponConditionSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_info_seq")
    CouponInfo couponInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    ConditionType conditionType;

    @Column(name = "main_value")
    String mainValue;

    @Column(name = "sub_value")
    String subValue;

    @Column(name = "condition_desc")
    String conditionDesc;

    public static CouponCondition create(CouponInfo couponInfo, ConditionType conditionType,
                                         String mainValue, String subValue, String conditionDesc) {
        return CouponCondition.builder()
            .couponInfo(couponInfo)
            .conditionType(conditionType)
            .mainValue(mainValue)
            .subValue(subValue)
            .conditionDesc(conditionDesc)
            .build();
    }

    public void updateCondition(ConditionType conditionType, String mainValue,
                                String subValue, String conditionDesc) {
        this.conditionType = conditionType;
        this.mainValue = mainValue;
        this.subValue = subValue;
        this.conditionDesc = conditionDesc;
    }

}