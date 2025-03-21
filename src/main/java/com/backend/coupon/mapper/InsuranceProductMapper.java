package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.InsuranceProductRes;
import com.backend.coupon.entity.InsuranceProduct;

public class InsuranceProductMapper {

    public static InsuranceProductRes toResponse(InsuranceProduct product) {
        return new InsuranceProductRes(
            product.getProductId(),
            product.getProductName(),
            product.getPremium(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

}