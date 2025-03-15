package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
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
}
