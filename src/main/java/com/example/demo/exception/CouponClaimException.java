package com.example.demo.exception;

public class CouponClaimException extends RuntimeException {
    public CouponClaimException(String message) {
        super(message);
    }
}