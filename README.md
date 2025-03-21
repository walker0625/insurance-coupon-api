# 쿠폰 관리 시스템

## 1. Code

    1) Data 흐름 : requestDto > entity(By Domain method) > responseDto(By Mapper)
    2) 주요 Logic : CouponActionService(쿠폰 발급/사용) 

## 2. API 기능
   
    1) 쿠폰 정보 관리
        - 쿠폰 정보 등록
        - 쿠폰 정보 목록 조회
        - 쿠폰 정보 단일 조회
        - 쿠폰 정보 수정
        - 쿠폰 정보 삭제

    2) 쿠폰 발급 및 사용
        - 쿠폰 발급
        - 쿠폰 사용

    3) 쿠폰 조건 관리
        - 쿠폰 조건 등록
        - 쿠폰 조건 수정

    4) 쿠폰 사용 이력 및 보유 현황
        - 쿠폰 이력 등록
        - 쿠폰 이력 목록 조회
        - 특정 사용자의 쿠폰 목록 조회

    5) 보험 상품 및 계약 정보 관리
        - 보험 상품 등록
        - 보험 계약 등록
   
## 3. API 문서

    API 문서 파일 위치 : /resources/api-doc.html
    서버 실행 후 http://localhost:8080/swagger-ui/index.html 에서 더 자세한 확인 및 test 가능

## 4. Test Code
    
    CouponActionService(쿠폰 발급/사용)를 Test(+실패 Case)

*** 쿠폰 사용 api 기능 구현 중 쿠폰 조건(CouponCondition) 적용에 쿠폰 발행 시점에 대한 정보가 필요한데<br>
해당 컬럼이 기존 Schema에 존재하지 않아 personal_coupon에 created_at 컬럼을 추가 하였습니다.