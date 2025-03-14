package com.example.demo.model;

import lombok.Data;

@Data
public class Coupon {
    private Long id;
    private String code;
    private boolean claimed;
}
