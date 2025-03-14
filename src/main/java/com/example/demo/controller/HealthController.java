package com.example.demo.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

@Component
public class HealthController implements HealthIndicator {
    
    @Override
    public Health health() {
        return Health.up().build();
    }
}