package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        createTables();
        insertCoupons();
    }

    private void createTables() {
        String createCouponsTable = """
            CREATE TABLE IF NOT EXISTS coupons (
                id SERIAL PRIMARY KEY,
                code VARCHAR(255) UNIQUE NOT NULL,
                assigned BOOLEAN DEFAULT FALSE
            )
        """;

        String createClaimsTable = """
            CREATE TABLE IF NOT EXISTS claims (
                id SERIAL PRIMARY KEY,
                coupon_id INT REFERENCES coupons(id),
                ip_address VARCHAR(255),
                browser_session VARCHAR(255),
                claim_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        jdbcTemplate.execute(createCouponsTable);
        jdbcTemplate.execute(createClaimsTable);
    }

    private void insertCoupons() {
        String countQuery = "SELECT COUNT(*) FROM coupons";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class);

        if (count != null && count < 20) {
            for (int i = 1; i <= 20; i++) {
                String couponCode = "COUPON" + i;
                jdbcTemplate.update("INSERT INTO coupons (code) VALUES (?) ON CONFLICT (code) DO NOTHING", couponCode);
            }
            System.out.println("✅ Inserted 20 coupons into the database.");
        } else {
            System.out.println("✅ Coupons already exist in the database.");
        }
    }
}
