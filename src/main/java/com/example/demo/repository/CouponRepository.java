package com.example.demo.repository;

import com.example.demo.model.Coupon;
import java.util.List;

public interface CouponRepository {
    List<Coupon> getAvailableCoupons();
    void assignCoupon(Long couponId);
    void logClaim(Long couponId, String ip, String session);
    boolean canClaim(String ip, String session);
    Long getTimeUntilNextClaim(String ip, String session);
}
