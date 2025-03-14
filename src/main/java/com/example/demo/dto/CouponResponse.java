package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponse {
    private boolean success;
    private String message;
    private String couponCode;
    private long waitTimeInSeconds;
}