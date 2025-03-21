package com.backend.coupon.dto.res;

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
public class InsuranceProductRes {

    UUID productId;

    String productName;

    Double premium;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}