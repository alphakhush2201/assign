package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorHandler implements ErrorController {
    
    @RequestMapping("/error")
    @ResponseBody
    public Map<String, Object> handleError(HttpServletRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        errorDetails.put("timestamp", System.currentTimeMillis());
        errorDetails.put("status", statusCode);
        errorDetails.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        errorDetails.put("message", errorMessage != null ? errorMessage : "No additional details available");
        errorDetails.put("path", requestUri != null ? requestUri : request.getRequestURI());
        
        return errorDetails;
    }
}