package com.backend.coupon.service;

import com.backend.coupon.dto.req.CouponInfoCreateReq;
import com.backend.coupon.dto.req.CouponInfoUpdateReq;
import com.backend.coupon.dto.res.CouponInfoCreateRes;
import com.backend.coupon.dto.res.CouponInfoRes;
import com.backend.coupon.entity.CouponInfo;
import com.backend.coupon.mapper.CouponInfoMapper;
import com.backend.coupon.repository.CouponInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponInfoService {

    @Autowired
    CouponInfoRepository couponInfoRepository;

    @Transactional(rollbackFor = Exception.class)
    public CouponInfoCreateRes createCouponInfo(CouponInfoCreateReq req) {

        CouponInfo couponInfo = CouponInfo.create(
            req.getCouponType(), req.getCouponName(), req.getTargetType(),
            req.getCouponPublishType(), req.getPurposeType(), req.getPurposeValue(),
            req.getCouponBadgeType(), req.getCouponImageUrl(), req.getLimitCount(),
            req.getIsAble(), req.getIsDuplicate(), req.getCouponStartAt(), req.getCouponEndAt()
        );

        CouponInfo saved = couponInfoRepository.save(couponInfo);

        return CouponInfoMapper.toCreateRes(saved);
    }

    public CouponInfoRes getCouponInfoById(Long id) {

        CouponInfo couponInfo = couponInfoRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("쿠폰 정보를 찾을 수 없습니다."));

        return CouponInfoMapper.toRes(couponInfo);
    }

    public List<CouponInfoRes> getAllCouponInfos() {

        return couponInfoRepository.findAll()
            .stream()
            .map(CouponInfoMapper::toRes)
            .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCouponInfo(Long id, CouponInfoUpdateReq req) {

        CouponInfo existingCouponInfo = couponInfoRepository.findById(id)
                                                            .orElseThrow(() -> new IllegalArgumentException("쿠폰 정보를 찾을 수 없습니다."));

        // 쿠폰의 사용 유형, 발급 유형, 조건 수정
        existingCouponInfo.updateCouponInfo(req.getCouponType(), req.getCouponPublishType(),
                                            req.getPurposeType(), req.getPurposeValue(), req.getLimitCount());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCouponInfo(Long id) {
        couponInfoRepository.deleteById(id);
    }

}