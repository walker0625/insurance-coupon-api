package com.backend.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.coupon.dto.req.InsuranceContractCreateReq;
import com.backend.coupon.dto.req.InsuranceProductCreateReq;
import com.backend.coupon.dto.res.InsuranceContractRes;
import com.backend.coupon.dto.res.InsuranceProductRes;
import com.backend.coupon.service.InsuranceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "보험 상품 및 계약 정보 관리")
@RequestMapping("/api/insurances")
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    @Operation(summary = "보험 상품 등록")
    @PostMapping
    public ResponseEntity<InsuranceProductRes> registerProduct(@RequestBody InsuranceProductCreateReq request) {

        return ResponseEntity.ok(insuranceService.registerProduct(request));
    }

    @Operation(summary = "보험 계약 등록")
    @PostMapping("/contract")
    public ResponseEntity<InsuranceContractRes> createContract(@RequestBody InsuranceContractCreateReq request) {

        return ResponseEntity.ok(insuranceService.createContract(request));
    }

}