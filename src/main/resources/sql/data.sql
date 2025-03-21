-- person 테이블 (사용자 정보)
INSERT INTO person (person_id, name, email, phone, created_at, updated_at)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', '김영희', 'younghee@example.com', '010-1234-5678', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440001', '이철수', 'cheolsu@example.com', '010-2345-6789', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440002', '박민지', 'minji@example.com', '010-3456-7890', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440003', '최준호', 'junho@example.com', '010-4567-8901', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440004', '정수진', 'sujin@example.com', '010-5678-9012', NOW(), NOW());

-- insurance_product 테이블 (보험 상품 정보)
INSERT INTO insurance_product (product_id, product_name, premium, created_at, updated_at)
VALUES
    ('6b3e8400-e29b-41d4-a716-446655440001', '가족 건강 보험', 50000.0, NOW(), NOW()),
    ('6b3e8400-e29b-41d4-a716-446655440002', '자동차 보험', 30000.0, NOW(), NOW()),
    ('6b3e8400-e29b-41d4-a716-446655440003', '여행자 보험', 15000.0, NOW(), NOW()),
    ('6b3e8400-e29b-41d4-a716-446655440004', '주택 화재 보험', 40000.0, NOW(), NOW()),
    ('6b3e8400-e29b-41d4-a716-446655440005', '종신 보험', 70000.0, NOW(), NOW());

-- insurance_contract 테이블 (보험 계약 정보)
INSERT INTO insurance_contract (contract_id, person_id, product_id, premium, contract_start_date, contract_end_date, status, created_at, updated_at)
VALUES
    ('7c4e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440000', '6b3e8400-e29b-41d4-a716-446655440001', 50000.0, NOW(), DATEADD('YEAR', 1, NOW()), '활성', NOW(), NOW()),
    ('7c4e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001', '6b3e8400-e29b-41d4-a716-446655440002', 30000.0, NOW(), DATEADD('YEAR', 1, NOW()), '활성', NOW(), NOW()),
    ('7c4e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002', '6b3e8400-e29b-41d4-a716-446655440003', 15000.0, NOW(), DATEADD('MONTH', 6, NOW()), '활성', NOW(), NOW()),
    ('7c4e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440003', '6b3e8400-e29b-41d4-a716-446655440004', 40000.0, NOW(), DATEADD('YEAR', 1, NOW()), '활성', NOW(), NOW()),
    ('7c4e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004', '6b3e8400-e29b-41d4-a716-446655440005', 70000.0, NOW(), DATEADD('YEAR', 5, NOW()), '활성', NOW(), NOW());

-- coupon_info 테이블 (쿠폰 정보)
INSERT INTO coupon_info (coupon_type, coupon_name, target_type, coupon_publish_type, purpose_type, purpose_value, coupon_badge_type, coupon_image_url, press_count, use_count, limit_count, is_able, is_duplicate, coupon_start_at, coupon_end_at)
VALUES
    ('ONCE', '10% 할인 쿠폰', 'ALL', 'POLY', 'RATE', 10.0, 'NEW', 'http://example.com/coupon1.jpg', 0, 0, 100, true, false, NOW(), DATEADD('DAY', 30, NOW())),
    ('MULTI', '5,000원 할인 쿠폰', 'B2C', 'UNI', 'AMOUNT', 5000.0, 'BEST', 'http://example.com/coupon2.jpg', 0, 0, 50, true, true, NOW(), DATEADD('DAY', 60, NOW())),
    ('LIMIT', '20% 할인 쿠폰', 'B2B', 'POLY', 'RATE', 20.0, 'NEW', 'http://example.com/coupon3.jpg', 0, 0, 20, true, false, NOW(), DATEADD('DAY', 15, NOW())),
    ('ONCE', '3,000원 할인 쿠폰', 'ALL', 'UNI', 'AMOUNT', 3000.0, 'BEST', 'http://example.com/coupon4.jpg', 0, 0, 200, true, false, NOW(), DATEADD('DAY', 45, NOW())),
    ('MULTI', '15% 할인 쿠폰', 'B2C', 'POLY', 'RATE', 15.0, 'NEW', 'http://example.com/coupon5.jpg', 0, 0, 75, true, true, NOW(), DATEADD('DAY', 90, NOW()));

-- coupon_condition 테이블 (쿠폰 조건)
INSERT INTO coupon_condition (coupon_info_seq, condition_type, main_value, sub_value, condition_desc)
VALUES
    (1, 'MAX_DISCOUNT', '10000', NULL, '최대 10,000원 할인'),
    (2, 'MIN_AMOUNT', '20000', NULL, '최소 20,000원 이상 구매 시 사용 가능'),
    (3, 'ONLY_PRODUCT', '6b3e8400-e29b-41d4-a716-446655440003', NULL, '여행자 보험 전용'),
    (4, 'TERM', '1', 'MONTH', '1개월 내 중복 사용 가능'),
    (5, 'EXPIRE', '30', 'DAY', '발급 후 30일 내 사용');

-- coupon_book 테이블 (쿠폰 코드)
INSERT INTO coupon_book (coupon_info_seq, coupon_code, is_used, expire_at)
VALUES
    (1, 'ABC1234567', false, DATEADD('DAY', 30, NOW())),
    (2, 'FIXED5000', false, DATEADD('DAY', 60, NOW())),
    (3, 'XYZ7890123', false, DATEADD('DAY', 15, NOW())),
    (4, 'FIXED3000', false, DATEADD('DAY', 45, NOW())),
    (5, 'PQR4567890', false, DATEADD('DAY', 90, NOW()));

-- personal_coupon 테이블 (사용자 보유 쿠폰)
INSERT INTO personal_coupon (person_coupon_id, person_id, coupon_code, use_at, insurance_subscription_details_id, use_data, created_at)
VALUES
    ('8d4e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440000', 'ABC1234567', NULL, NULL, NULL, NOW()),
    ('8d4e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001', 'FIXED5000', NULL, NULL, NULL, NOW()),
    ('8d4e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002', 'XYZ7890123', NOW(), '7c4e8400-e29b-41d4-a716-446655440003', '여행자 보험 할인 적용', NOW()),
    ('8d4e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440003', 'FIXED3000', NULL, NULL, NULL, NOW()),
    ('8d4e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004', 'PQR4567890', NULL, NULL, NULL, NOW());

-- coupon_log 테이블 (쿠폰 로그)
INSERT INTO coupon_log (coupon_code, person_id, log_type, log_desc)
VALUES
    ('ABC1234567', '550e8400-e29b-41d4-a716-446655440000', 'PUBLISH', '10% 할인 쿠폰 발급'),
    ('FIXED5000', '550e8400-e29b-41d4-a716-446655440001', 'PUBLISH', '5,000원 할인 쿠폰 발급'),
    ('XYZ7890123', '550e8400-e29b-41d4-a716-446655440002', 'USE', '20% 할인 쿠폰 사용 - 여행자 보험'),
    ('FIXED3000', '550e8400-e29b-41d4-a716-446655440003', 'PUBLISH', '3,000원 할인 쿠폰 발급'),
    ('PQR4567890', '550e8400-e29b-41d4-a716-446655440004', 'PUBLISH', '15% 할인 쿠폰 발급');