package com.backend.coupon.dto.req;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceContractCreateReq {

    UUID personId;

    UUID productId;

    LocalDateTime contractStartDate;

    LocalDateTime contractEndDate;

    String couponCode; // 선택적

}
