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
DELETE FROM claims;
DELETE FROM coupons;

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
('LOYALTY20', FALSE);

-- Insert additional coupons using a loop approach for H2
-- H2 doesn't support generate_series, so we'll use a different approach
INSERT INTO coupons (code, assigned) VALUES ('COUPON001', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON002', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON003', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON004', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON005', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON006', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON007', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON008', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON009', FALSE);
INSERT INTO coupons (code, assigned) VALUES ('COUPON010', FALSE);
-- Add more coupons as needed