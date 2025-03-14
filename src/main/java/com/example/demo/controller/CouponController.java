package com.example.demo.controller;

import com.example.demo.service.CouponService;
import com.example.demo.dto.CouponResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/claim")
    public ResponseEntity<CouponResponse> claimCoupon(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String session = request.getSession().getId();
        
        CouponResponse response = couponService.claimCoupon(ip, session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remaining-time")
    public ResponseEntity<Long> getRemainingTime(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String session = request.getSession().getId();
        
        long remainingTime = couponService.getRemainingTimeForNextClaim(ip, session);
        return ResponseEntity.ok(remainingTime);
    }
}
