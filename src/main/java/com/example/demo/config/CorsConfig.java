package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/coupons/**")
                .allowedOrigins("https://coupon-frontend-seven.vercel.app/")  // Added trailing slash
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("Accept", "Content-Type", "Authorization", "Origin")  // Added Origin header
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}