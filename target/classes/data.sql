-- Create tables
CREATE TABLE IF NOT EXISTS coupons (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    assigned BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS claims (
    id SERIAL PRIMARY KEY,
    coupon_id BIGINT REFERENCES coupons(id),
    ip_address VARCHAR(45) NOT NULL,
    browser_session VARCHAR(100) NOT NULL,
    claim_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Clear existing data
TRUNCATE TABLE claims CASCADE;
TRUNCATE TABLE coupons CASCADE;

-- Insert predefined coupons
INSERT INTO coupons (code, assigned) 
VALUES 
('SPRING25', FALSE),
('SUMMER10', FALSE),
('FALL15', FALSE),
('WINTER20', FALSE),
('WELCOME50', FALSE),
('FLASH30', FALSE),
('SPECIAL40', FALSE),
('HOLIDAY25', FALSE),
('NEWUSER15', FALSE),
('LOYALTY20', FALSE)
ON CONFLICT (code) DO NOTHING;

-- Insert 100 unique coupons
INSERT INTO coupons (code, assigned)
SELECT 
    'COUPON' || LPAD(CAST(generate_series AS TEXT), 3, '0'),
    FALSE
FROM generate_series(1, 100);