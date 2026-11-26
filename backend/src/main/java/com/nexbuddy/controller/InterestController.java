package com.nexbuddy.controller;

import com.nexbuddy.dto.common.ApiResponse;
import com.nexbuddy.dto.user.InterestDto;
import com.nexbuddy.service.InterestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
public class InterestController {

    private final InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping
    public ApiResponse<List<InterestDto>> getAvailableInterests() {
        return ApiResponse.ok(interestService.getAllInterests());
    }
}
