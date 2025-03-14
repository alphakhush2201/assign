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

CREATE INDEX idx_claims_ip_session ON claims(ip_address, browser_session);
CREATE INDEX idx_claims_time ON claims(claim_time);