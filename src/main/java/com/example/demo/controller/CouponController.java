package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CouponController {
    // Track IP addresses and their last claim time
    private final Map<String, Long> ipLastClaimTime = new ConcurrentHashMap<>();
    // Track browser sessions and their last claim time
    private final Map<String, Long> sessionLastClaimTime = new ConcurrentHashMap<>();
    // Time window for restrictions (1 hour in milliseconds)
    private static final long CLAIM_TIMEOUT = 3600000;
    
    private int currentCouponIndex = 0;
    private final List<String> availableCoupons = Arrays.asList(
        "WELCOME50", "SPRING25", "SUMMER10", "FALL15", "WINTER20"
    );

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
            
            // Check IP address restriction
            if (isIpRestricted(ipAddress)) {
                long waitTime = getWaitTimeInSeconds(ipLastClaimTime.get(ipAddress));
                response.put("success", false);
                response.put("message", "Please wait " + waitTime + " seconds before claiming another coupon");
                response.put("waitTimeInSeconds", waitTime);
                return response;
            }
            
            // Check browser session restriction
            if (isSessionRestricted(browserSession)) {
                long waitTime = getWaitTimeInSeconds(sessionLastClaimTime.get(browserSession));
                response.put("success", false);
                response.put("message", "Please wait " + waitTime + " seconds before claiming another coupon");
                response.put("waitTimeInSeconds", waitTime);
                return response;
            }
            
            // Get next available coupon in round-robin fashion
            String couponCode = getNextCoupon();
            
            // Update restriction trackers
            ipLastClaimTime.put(ipAddress, System.currentTimeMillis());
            sessionLastClaimTime.put(browserSession, System.currentTimeMillis());
            
            response.put("success", true);
            response.put("couponCode", couponCode);
            response.put("message", "Coupon claimed successfully");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to claim coupon: " + e.getMessage());
        }
        
        return response;
    }
    
    private synchronized String getNextCoupon() {
        String coupon = availableCoupons.get(currentCouponIndex);
        currentCouponIndex = (currentCouponIndex + 1) % availableCoupons.size();
        return coupon;
    }
    
    private boolean isIpRestricted(String ipAddress) {
        Long lastClaimTime = ipLastClaimTime.get(ipAddress);
        return lastClaimTime != null && 
               System.currentTimeMillis() - lastClaimTime < CLAIM_TIMEOUT;
    }
    
    private boolean isSessionRestricted(String sessionId) {
        Long lastClaimTime = sessionLastClaimTime.get(sessionId);
        return lastClaimTime != null && 
               System.currentTimeMillis() - lastClaimTime < CLAIM_TIMEOUT;
    }
    
    private long getWaitTimeInSeconds(Long lastClaimTime) {
        long remainingTime = CLAIM_TIMEOUT - (System.currentTimeMillis() - lastClaimTime);
        return Math.max(0, remainingTime / 1000);
    }
}
