package com.backend.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.backend.coupon.enums.CouponBadgeType;
import com.backend.coupon.enums.CouponPublishType;
import com.backend.coupon.enums.CouponType;
import com.backend.coupon.enums.PurposeType;
import com.backend.coupon.enums.TargetType;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon_info")
public class CouponInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_info_seq")
    Long couponInfoSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type")
    CouponType couponType;

    @Column(name = "coupon_name")
    String couponName;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    TargetType targetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_publish_type")
    CouponPublishType couponPublishType;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose_type")
    PurposeType purposeType;

    @Column(name = "purpose_value")
    Double purposeValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_badge_type")
    CouponBadgeType couponBadgeType;

    @Column(name = "coupon_image_url")
    String couponImageUrl;

    @Builder.Default
    @Column(name = "press_count")
    Integer pressCount = 0;

    @Builder.Default
    @Column(name = "use_count")
    Integer useCount = 0;

    @Column(name = "limit_count")
    Integer limitCount;

    @Builder.Default
    @Column(name = "is_able")
    Boolean isAble = true;

    @Builder.Default
    @Column(name = "is_duplicate")
    Boolean isDuplicate = false;

    @Column(name = "coupon_start_at")
    LocalDateTime couponStartAt;

    @Column(name = "coupon_end_at")
    LocalDateTime couponEndAt;

    @Builder.Default
    @OneToMany(mappedBy = "couponInfo", fetch = FetchType.LAZY)
    private List<CouponCondition> conditions = new ArrayList<>();

    public static CouponInfo create(CouponType couponType, String couponName, TargetType targetType,
                                    CouponPublishType couponPublishType, PurposeType purposeType, Double purposeValue,
                                    CouponBadgeType couponBadgeType, String couponImageUrl, Integer limitCount,
                                    Boolean isAble, Boolean isDuplicate, LocalDateTime couponStartAt, LocalDateTime couponEndAt) {
        return CouponInfo.builder()
            .couponType(couponType)
            .couponName(couponName)
            .targetType(targetType)
            .couponPublishType(couponPublishType)
            .purposeType(purposeType)
            .purposeValue(purposeValue)
            .couponBadgeType(couponBadgeType)
            .couponImageUrl(couponImageUrl)
            .limitCount(limitCount)
            .isAble(isAble)
            .isDuplicate(isDuplicate)
            .couponStartAt(couponStartAt)
            .couponEndAt(couponEndAt)
            .build();
    }

    public void updateCouponInfo(CouponType couponType, CouponPublishType couponPublishType,
                                 PurposeType purposeType, Double purposeValue, Integer limitCount) {
        this.couponType = couponType;
        this.couponPublishType = couponPublishType;
        this.purposeType = purposeType;
        this.purposeValue = purposeValue;
        this.limitCount = limitCount;
    }

    public void incrementPressCount() {
        this.pressCount++;
    }

    public void incrementUseCount() {
        this.useCount++;
    }

    public double calculateDiscount(InsuranceContract contract) {

        if (purposeType == PurposeType.RATE) {
            return contract.getPremium() * (purposeValue / 100.0);
        } else if (purposeType == PurposeType.AMOUNT) {
            return purposeValue;
        }

        throw new IllegalStateException("지원되지 않는 할인 목적(purposeType): " + purposeType);
    }

    public void validateConditions(InsuranceContract contract, PersonalCoupon personalCoupon) {

        LocalDateTime now = LocalDateTime.now();
        double discountAmount = calculateDiscount(contract);
        LocalDateTime issueTime = personalCoupon.getCreatedAt();

        for (CouponCondition condition : conditions) {
            switch (condition.getConditionType()) {

                case MAX_DISCOUNT:

                    double maxDiscount = Double.parseDouble(condition.getMainValue());

                    if (discountAmount > maxDiscount) {
                        throw new IllegalStateException("최대 할인 금액 초과: 최대 " + maxDiscount + "원, 계산된 할인: " + discountAmount + "원");
                    }

                    break;

                case MIN_AMOUNT:

                    double minAmount = Double.parseDouble(condition.getMainValue());

                    if (contract.getPremium() < minAmount) {
                        throw new IllegalStateException("최소 구매 금액 조건 불만족: " + minAmount + "원 이상 필요");
                    }

                    break;

                case ONLY_MEMBER:

                    // 비회원 할인 케이스가 존재하는 경우 로직 추가

                    break;

                case ONLY_PRODUCT:

                    if (!contract.getProduct().getProductId().toString().equals(condition.getMainValue())) {
                        throw new IllegalStateException("지정된 상품이 아님: " + condition.getMainValue());
                    }

                    break;

                case TERM:

                    int termValue = Integer.parseInt(condition.getMainValue());
                    String termUnit = condition.getSubValue();
                    LocalDateTime termLimit = calculateLimit(issueTime, termValue, termUnit);

                    if (now.isAfter(termLimit)) {
                        throw new IllegalStateException("사용 기간 조건 불만족: " + termValue + " " + termUnit + " 내 사용 필요");
                    }

                    break;

                case EXPIRE:

                    int expireValue = Integer.parseInt(condition.getMainValue());
                    String expireUnit = condition.getSubValue();
                    LocalDateTime expireLimit = calculateLimit(issueTime, expireValue, expireUnit);

                    if (now.isAfter(expireLimit)) {
                        throw new IllegalStateException("만료 조건 불만족: 발급 후 " + expireValue + " " + expireUnit + " 내 사용 필요");
                    }

                    break;

                default:
                    break;
            }
        }
    }

    private LocalDateTime calculateLimit(LocalDateTime baseTime, int value, String unit) {

        return switch (unit.toUpperCase()) {
            case "YEAR" -> baseTime.plusYears(value);
            case "MONTH" -> baseTime.plusMonths(value);
            case "DAY" -> baseTime.plusDays(value);
            default -> throw new IllegalArgumentException("지원되지 않는 시간 단위: " + unit);
        };

    }

}