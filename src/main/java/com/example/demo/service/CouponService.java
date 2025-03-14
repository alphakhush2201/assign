


package com.example.demo.service;

import com.example.demo.dto.CouponResponse;

public interface CouponService {
    CouponResponse claimCoupon(String ip, String session);
    long getRemainingTimeForNextClaim(String ip, String session);
    boolean isEligibleForClaim(String ip, String session);
}
