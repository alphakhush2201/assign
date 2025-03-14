package com.example.demo.repository;

import com.example.demo.model.Coupon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;

@Repository
public class CouponRepositoryImpl implements CouponRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final long HOUR_IN_MILLISECONDS = 3600000;

    private final RowMapper<Coupon> couponMapper = (rs, rowNum) -> {
        Coupon coupon = new Coupon();
        coupon.setId(rs.getLong("id"));
        coupon.setCode(rs.getString("code"));
        coupon.setClaimed(rs.getBoolean("assigned"));
        return coupon;
    };

    public CouponRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Coupon> getAvailableCoupons() {
        String query = "SELECT * FROM coupons WHERE assigned = FALSE ORDER BY id ASC";
        return jdbcTemplate.query(query, couponMapper);
    }

    @Override
    public void assignCoupon(Long couponId) {
        String query = "UPDATE coupons SET assigned = TRUE WHERE id = ?";
        jdbcTemplate.update(query, couponId);
    }

    @Override
    public void logClaim(Long couponId, String ip, String session) {
        String query = "INSERT INTO claims (coupon_id, ip_address, browser_session, claim_time) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(query, couponId, ip, session);
    }

    @Override
    public boolean canClaim(String ip, String session) {
        String query = "SELECT COUNT(*) FROM claims WHERE (ip_address = ? OR browser_session = ?) AND claim_time > NOW() - INTERVAL '1 hour'";
        int count = jdbcTemplate.queryForObject(query, Integer.class, ip, session);
        return count == 0;
    }

    @Override
    public Long getTimeUntilNextClaim(String ip, String session) {
        String query = "SELECT MAX(claim_time) FROM claims WHERE ip_address = ? OR browser_session = ?";
        java.sql.Timestamp lastClaim = jdbcTemplate.queryForObject(query, java.sql.Timestamp.class, ip, session);
        
        if (lastClaim == null) {
            return 0L;
        }

        long elapsedTime = System.currentTimeMillis() - lastClaim.getTime();
        return Math.max(0, HOUR_IN_MILLISECONDS - elapsedTime);
    }
}