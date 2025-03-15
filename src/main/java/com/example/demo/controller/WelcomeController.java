package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the Coupon Service API");
        response.put("status", "running");
        response.put("endpoints", Arrays.asList("/health", "/api/coupons"));
        return response;
    }
}