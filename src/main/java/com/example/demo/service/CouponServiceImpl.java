package com.example.demo.service;

import com.example.demo.dto.CouponResponse;
import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import com.example.demo.exception.CouponClaimException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private static final long COOLDOWN_PERIOD = 3600000; // 1 hour in milliseconds

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponResponse claimCoupon(String ip, String session) {
        if (!isEligibleForClaim(ip, session)) {
            long remainingTime = getRemainingTimeForNextClaim(ip, session);
            return CouponResponse.builder()
                    .success(false)
                    .message("Please wait " + (remainingTime / 1000) + " seconds before claiming another coupon")
                    .code(null)
                    .waitTimeInSeconds(remainingTime / 1000)
                    .build();
        }

        List<Coupon> availableCoupons = couponRepository.getAvailableCoupons();
        if (availableCoupons.isEmpty()) {
            throw new CouponClaimException("No coupons available!");
        }

        Coupon coupon = availableCoupons.get(0);
        couponRepository.assignCoupon(coupon.getId());
        couponRepository.logClaim(coupon.getId(), ip, session);

        return CouponResponse.builder()
                .success(true)
                .message("Successfully claimed coupon")
                .code(coupon.getCode())  // Changed from couponCode to code
                .waitTimeInSeconds(0L)   // Added waitTimeInSeconds
                .build();
    }

    @Override
    public long getRemainingTimeForNextClaim(String ip, String session) {
Long lastClaimTime = couponRepository.getTimeUntilNextClaim(ip, session);
        if (lastClaimTime == null) {
            return 0;
        }
        long elapsedTime = System.currentTimeMillis() - lastClaimTime;
        return Math.max(0, COOLDOWN_PERIOD - elapsedTime);
    }

    @Override
    public boolean isEligibleForClaim(String ip, String session) {
        return getRemainingTimeForNextClaim(ip, session) == 0;
    }
}