package com.backend.coupon.mapper;

import com.backend.coupon.dto.res.InsuranceContractRes;
import com.backend.coupon.entity.InsuranceContract;

public class InsuranceContractMapper {

    public static InsuranceContractRes toResponse(InsuranceContract contract) {
        return new InsuranceContractRes(
            contract.getContractId(),
            contract.getPerson().getPersonId(),
            contract.getProduct().getProductId(),
            contract.getPremium(),
            contract.getContractStartDate(),
            contract.getContractEndDate(),
            contract.getStatus(),
            contract.getCreatedAt(),
            contract.getUpdatedAt()
        );
    }

}