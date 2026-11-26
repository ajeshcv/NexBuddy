package com.nexbuddy.controller;

import com.nexbuddy.dto.admin.AdminDashboardDto;
import com.nexbuddy.dto.admin.AdminUserDto;
import com.nexbuddy.dto.common.ApiResponse;
import com.nexbuddy.service.AdminService;
import com.nexbuddy.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardDto> getDashboard() {
        return ApiResponse.ok(adminService.getDashboardStats());
    }

    @GetMapping("/users")
    public ApiResponse<List<AdminUserDto>> getAllUsers() {
        return ApiResponse.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{userId}/suspend")
    public ApiResponse<Void> suspendUser(@PathVariable UUID userId) {
        adminService.suspendUser(userId, SecurityUtils.getCurrentUserId());
        return ApiResponse.okMessage("User suspended successfully.");
    }

    @PutMapping("/users/{userId}/activate")
    public ApiResponse<Void> activateUser(@PathVariable UUID userId) {
        adminService.activateUser(userId);
        return ApiResponse.okMessage("User activated successfully.");
    }
}
