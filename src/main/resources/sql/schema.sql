CREATE TABLE coupon_info (
    coupon_info_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_type VARCHAR(255),
    coupon_name VARCHAR(255),
    target_type VARCHAR(255),
    coupon_publish_type VARCHAR(255),
    purpose_type VARCHAR(255),
    purpose_value DOUBLE,
    coupon_badge_type VARCHAR(255),
    coupon_image_url VARCHAR(255),
    press_count INT,
    use_count INT,
    limit_count INT,
    is_able BOOLEAN,
    is_duplicate BOOLEAN,
    coupon_start_at TIMESTAMP,
    coupon_end_at TIMESTAMP
);

CREATE TABLE coupon_condition (
    coupon_condition_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_info_seq BIGINT,
    condition_type VARCHAR(255),
    main_value VARCHAR(255),
    sub_value VARCHAR(255),
    condition_desc VARCHAR(255),
    FOREIGN KEY (coupon_info_seq) REFERENCES coupon_info(coupon_info_seq)
);

CREATE TABLE coupon_book (
    coupon_book_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_info_seq BIGINT,
    coupon_code VARCHAR(255) UNIQUE,
    is_used BOOLEAN,
    expire_at TIMESTAMP,
    FOREIGN KEY (coupon_info_seq) REFERENCES coupon_info(coupon_info_seq)
);

CREATE TABLE person (
    person_id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE personal_coupon (
    person_coupon_id UUID PRIMARY KEY,
    person_id UUID,
    coupon_code VARCHAR(255),
    use_at TIMESTAMP,
    insurance_subscription_details_id UUID,
    use_data VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 발행 시점 추가
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);

CREATE TABLE coupon_log (
    coupon_log_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_code VARCHAR(255),
    person_id UUID,
    log_type VARCHAR(255),
    log_desc VARCHAR(255),
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);

CREATE TABLE insurance_product (
    product_id UUID PRIMARY KEY,
    product_name VARCHAR(255),
    premium DOUBLE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE insurance_contract (
    contract_id UUID PRIMARY KEY,
    person_id UUID,
    product_id UUID,
    premium DOUBLE,
    contract_start_date TIMESTAMP,
    contract_end_date TIMESTAMP,
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (product_id) REFERENCES insurance_product(product_id)
);

ALTER TABLE personal_coupon
ADD FOREIGN KEY (insurance_subscription_details_id) REFERENCES insurance_contract(contract_id);