package com.example.demo.service;

import com.example.demo.dto.CouponResponse;
import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    
    private final CouponRepository couponRepository;
    private static final long COOLDOWN_PERIOD = 3600000; // 1 hour in milliseconds
    
    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }
    
    @Override
    @Transactional
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
            return CouponResponse.builder()
                    .success(false)
                    .message("No coupons available!")
                    .code(null)
                    .waitTimeInSeconds(0L)
                    .build();
        }
        
        Coupon coupon = availableCoupons.get(0);
        couponRepository.assignCoupon(coupon.getId());
        couponRepository.logClaim(coupon.getId(), ip, session);
        
        return CouponResponse.builder()
                .success(true)
                .message("Successfully claimed coupon")
                .code(coupon.getCode())
                .waitTimeInSeconds(0L)
                .build();
    }
    
    @Override
    public long getRemainingTimeForNextClaim(String ip, String session) {
        return couponRepository.getTimeUntilNextClaim(ip, session);
    }
    
    @Override
    public boolean isEligibleForClaim(String ip, String session) {
        return couponRepository.canClaim(ip, session);
    }
}