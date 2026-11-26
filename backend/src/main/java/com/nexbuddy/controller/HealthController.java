package com.nexbuddy.controller;

import com.nexbuddy.dto.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.ok(
                "NexBuddy API is running",
                Map.of(
                        "status", "UP",
                        "service", "nexbuddy-backend",
                        "version", "1.0.0",
                        "timestamp", Instant.now().toString()
                )
        );
    }
}
