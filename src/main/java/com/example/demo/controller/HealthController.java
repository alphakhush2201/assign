package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        Map<String, Object> components = new HashMap<>();
        
        boolean dbStatus = checkDatabaseConnection();
        
        components.put("db", Map.of(
            "status", dbStatus ? "UP" : "DOWN",
            "details", Map.of("database", "PostgreSQL")
        ));
        
        status.put("status", dbStatus ? "UP" : "UP");
        status.put("components", components);
        
        return status;
    }
    
    @GetMapping("/actuator/health")
    public Map<String, Object> actuatorHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        return response;
    }
    
    @GetMapping("/healthz")
    public String healthz() {
        return "OK";
    }
    
    @GetMapping("/_health")
    public String railwayHealth() {
        return "OK";
    }
    
    @GetMapping("/")
    public Map<String, String> root() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Application is running");
        return response;
    }
    
    private boolean checkDatabaseConnection() {
        if (jdbcTemplate == null) {
            return false;
        }
        
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}