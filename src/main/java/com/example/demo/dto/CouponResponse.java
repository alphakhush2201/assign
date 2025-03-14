package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private String message;
    private String code;
    private boolean success;
    private Long waitTimeInSeconds;
}