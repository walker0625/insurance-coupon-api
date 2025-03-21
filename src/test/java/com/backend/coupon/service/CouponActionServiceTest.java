package com.backend.coupon.service;

import com.backend.coupon.dto.req.CouponIssueReq;
import com.backend.coupon.dto.req.CouponUseReq;
import com.backend.coupon.dto.res.CouponIssueRes;
import com.backend.coupon.entity.*;
import com.backend.coupon.enums.PurposeType;
import com.backend.coupon.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponActionServiceTest {

    @InjectMocks
    private CouponActionService couponActionService;

    @Mock
    private CouponInfoRepository couponInfoRepository;

    @Mock
    private CouponBookRepository couponBookRepository;

    @Mock
    private PersonalCouponRepository personalCouponRepository;

    @Mock
    private CouponLogRepository couponLogRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private InsuranceContractRepository insuranceContractRepository;

    private CouponInfo couponInfo;
    private Person person;
    private InsuranceContract contract;
    private CouponBook couponBook;
    private PersonalCoupon personalCoupon;

    // 테스트용 객체 초기화
    @BeforeEach
    void setUp() {

        couponInfo = CouponInfo.builder()
            .couponInfoSeq(1L)
            .couponName("Test Coupon")
            .isAble(true)
            .purposeType(PurposeType.AMOUNT)
            .purposeValue(1000.0)
            .limitCount(10)
            .pressCount(0)
            .useCount(0)
            .couponEndAt(LocalDateTime.now().plusDays(10))
            .build();

        person = Person.builder()
            .personId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
            .name("Test User")
            .build();

        contract = InsuranceContract.builder()
            .contractId(UUID.fromString("7c4e8400-e29b-41d4-a716-446655440001"))
            .person(person)
            .premium(10000.0)
            .status("활성")
            .contractStartDate(LocalDateTime.now())
            .contractEndDate(LocalDateTime.now().plusYears(1))
            .build();

        couponBook = CouponBook.builder()
            .couponBookSeq(1L)
            .couponInfo(couponInfo)
            .couponCode("TEST123456")
            .isUsed(false)
            .expireAt(LocalDateTime.now().plusDays(10))
            .build();

        personalCoupon = PersonalCoupon.builder()
            .personCouponId(UUID.randomUUID())
            .person(person)
            .couponCode("TEST123456")
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    @Transactional
    void 쿠폰_발급_성공_테스트() {

        // Given
        CouponIssueReq req = new CouponIssueReq();
        req.setCouponInfoId(1L);
        req.setPersonId(person.getPersonId());

        when(couponInfoRepository.findById(1L)).thenReturn(Optional.of(couponInfo));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(couponBookRepository.save(any(CouponBook.class))).thenReturn(couponBook);
        when(personalCouponRepository.save(any(PersonalCoupon.class))).thenReturn(personalCoupon);
        when(couponLogRepository.save(any(CouponLog.class))).thenReturn(mock(CouponLog.class));

        // When
        CouponIssueRes response = couponActionService.issueCoupon(req);

        // Then
        assertNotNull(response);
        assertNotNull(response.getCouponCode());
        assertEquals(1, couponInfo.getPressCount());
    }

    @Test
    void 발급_불가능한_쿠폰_예외_테스트() {

        // Given
        couponInfo = couponInfo.builder()
            .couponInfoSeq(1L)
            .isAble(false) // 발급 불가 상태
            .limitCount(10)
            .pressCount(0)
            .build();

        CouponIssueReq req = new CouponIssueReq();
        req.setCouponInfoId(1L);
        req.setPersonId(person.getPersonId());

        when(couponInfoRepository.findById(1L)).thenReturn(Optional.of(couponInfo));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            couponActionService.issueCoupon(req);
        });

        assertEquals("발급 가능한 쿠폰이 아닙니다.", exception.getMessage());
    }

    @Test
    void 쿠폰_한도_초과_예외_테스트() {

        // Given
        couponInfo = couponInfo.builder()
            .couponInfoSeq(1L)
            .isAble(true)
            .limitCount(5)
            .pressCount(5) // 한도 초과
            .build();

        CouponIssueReq req = new CouponIssueReq();
        req.setCouponInfoId(1L);
        req.setPersonId(person.getPersonId());

        when(couponInfoRepository.findById(1L)).thenReturn(Optional.of(couponInfo));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            couponActionService.issueCoupon(req);
        });

        assertEquals("발급 가능한 쿠폰이 아닙니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 쿠폰_사용_성공_테스트() {

        // Given
        CouponUseReq req = new CouponUseReq();
        req.setCouponCode("TEST123456");
        req.setPersonId(person.getPersonId());
        req.setContractId(contract.getContractId());

        when(couponBookRepository.findByCouponCode("TEST123456")).thenReturn(Optional.of(couponBook));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(insuranceContractRepository.findById(contract.getContractId())).thenReturn(Optional.of(contract));
        when(personalCouponRepository.findByPersonPersonIdAndCouponCode(person.getPersonId(), "TEST123456"))
                                    .thenReturn(personalCoupon);
        when(couponLogRepository.save(any(CouponLog.class))).thenReturn(mock(CouponLog.class));

        // When
        couponActionService.useCoupon(req);

        // Then
        assertTrue(couponBook.getIsUsed());
        assertEquals(9000.0, contract.getPremium()); // 10000 - 1000 할인
        assertEquals(1, couponInfo.getUseCount());
        assertNotNull(personalCoupon.getUseAt());
        assertEquals(contract, personalCoupon.getInsuranceContract());
    }

    @Test
    void 사용_불가능한_쿠폰_예외_테스트() {

        // Given
        couponBook = CouponBook.builder()
            .couponBookSeq(1L)
            .couponInfo(couponInfo)
            .couponCode("TEST123456")
            .isUsed(true) // 이미 사용됨
            .expireAt(LocalDateTime.now().plusDays(10))
            .build();

        CouponUseReq req = new CouponUseReq();
        req.setCouponCode("TEST123456");
        req.setPersonId(person.getPersonId());
        req.setContractId(contract.getContractId());

        when(couponBookRepository.findByCouponCode("TEST123456")).thenReturn(Optional.of(couponBook));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(insuranceContractRepository.findById(contract.getContractId())).thenReturn(Optional.of(contract));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            couponActionService.useCoupon(req);
        });

        assertEquals("사용 불가능한 쿠폰입니다.", exception.getMessage());
    }

    @Test
    void 만료된_쿠폰_예외_테스트() {

        // Given
        couponBook = CouponBook.builder()
            .couponBookSeq(1L)
            .couponInfo(couponInfo)
            .couponCode("TEST123456")
            .isUsed(false)
            .expireAt(LocalDateTime.now().minusDays(1)) // 만료됨
            .build();

        CouponUseReq req = new CouponUseReq();
        req.setCouponCode("TEST123456");
        req.setPersonId(person.getPersonId());
        req.setContractId(contract.getContractId());

        when(couponBookRepository.findByCouponCode("TEST123456")).thenReturn(Optional.of(couponBook));
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(insuranceContractRepository.findById(contract.getContractId())).thenReturn(Optional.of(contract));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            couponActionService.useCoupon(req);
        });

        assertEquals("사용 불가능한 쿠폰입니다.", exception.getMessage());
    }

}