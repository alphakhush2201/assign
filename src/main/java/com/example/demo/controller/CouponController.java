package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CouponController {

    @GetMapping("/coupons")
    public Map<String, Object> getAllCoupons() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> coupons = new ArrayList<>();
        
        // Add sample coupons (you can modify this to fetch from your database)
        Map<String, Object> coupon1 = new HashMap<>();
        coupon1.put("code", "WELCOME50");
        coupon1.put("assigned", false);
        coupons.add(coupon1);

        Map<String, Object> coupon2 = new HashMap<>();
        coupon2.put("code", "SPRING25");
        coupon2.put("assigned", false);
        coupons.add(coupon2);

        response.put("status", "success");
        response.put("message", "Coupons retrieved successfully");
        response.put("data", coupons);
        
        return response;
    }

    @PostMapping("/coupons/claim")
    public Map<String, Object> claimCoupon(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String browserSession = request.get("browserSession");
            String ipAddress = request.get("ipAddress");

            // For now, return a test coupon
            response.put("success", true);
            response.put("couponCode", "TEST123");
            response.put("message", "Coupon claimed successfully");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to claim coupon: " + e.getMessage());
        }
        
        return response;
    }
}
