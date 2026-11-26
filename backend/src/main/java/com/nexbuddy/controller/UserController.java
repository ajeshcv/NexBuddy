package com.nexbuddy.controller;

import com.nexbuddy.dto.common.ApiResponse;
import com.nexbuddy.dto.user.*;
import com.nexbuddy.service.UserService;
import com.nexbuddy.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> getMyProfile() {
        return ApiResponse.ok(userService.getMyProfile(SecurityUtils.getCurrentUserId()));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserProfileResponse> getUserProfile(@PathVariable UUID userId) {
        return ApiResponse.ok(userService.getUserProfile(userId, SecurityUtils.getCurrentUserId()));
    }

    @PutMapping("/profile")
    public ApiResponse<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse updated = userService.updateProfile(SecurityUtils.getCurrentUserId(), request);
        return ApiResponse.ok("Profile updated successfully.", updated);
    }

    @PostMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        String url = userService.uploadProfilePicture(SecurityUtils.getCurrentUserId(), file);
        return ApiResponse.ok("Profile picture updated successfully.", Map.of("profilePicture", url));
    }

    @PostMapping(value = "/cover-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadCoverPhoto(@RequestParam("file") MultipartFile file) {
        String url = userService.uploadCoverPhoto(SecurityUtils.getCurrentUserId(), file);
        return ApiResponse.ok("Cover photo updated successfully.", Map.of("coverPhoto", url));
    }

    @PutMapping("/interests")
    public ApiResponse<List<InterestDto>> updateInterests(@Valid @RequestBody UpdateInterestsRequest request) {
        List<InterestDto> updated = userService.updateInterests(SecurityUtils.getCurrentUserId(), request);
        return ApiResponse.ok("Interests updated successfully.", updated);
    }

    @GetMapping("/settings")
    public ApiResponse<UserSettingsDto> getSettings() {
        return ApiResponse.ok(userService.getSettings(SecurityUtils.getCurrentUserId()));
    }

    @PutMapping("/settings")
    public ApiResponse<UserSettingsDto> updateSettings(@RequestBody UpdateSettingsRequest request) {
        UserSettingsDto updated = userService.updateSettings(SecurityUtils.getCurrentUserId(), request);
        return ApiResponse.ok("Settings updated successfully.", updated);
    }

    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(SecurityUtils.getCurrentUserId(), request);
        return ApiResponse.okMessage("Password changed successfully.");
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> deleteMyAccount() {
        userService.deleteMyAccount(SecurityUtils.getCurrentUserId());
        return ApiResponse.okMessage("Account deleted successfully.");
    }
}
