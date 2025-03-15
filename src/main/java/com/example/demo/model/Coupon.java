package com.example.demo.model;

public class Coupon {
    private Long id;
    private String code;
    private boolean claimed;

    public Coupon() {
    }

    public Coupon(Long id, String code, boolean claimed) {
        this.id = id;
        this.code = code;
        this.claimed = claimed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
}
