package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the Coupon Service API");
        response.put("endpoints", new String[]{"/health", "/api/coupons"});
        response.put("status", "running");
        return response;
    }
}