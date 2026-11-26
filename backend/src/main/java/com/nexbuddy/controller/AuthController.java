package com.nexbuddy.controller;

import com.nexbuddy.dto.auth.*;
import com.nexbuddy.dto.common.ApiResponse;
import com.nexbuddy.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<OtpDispatchDto>> register(@Valid @RequestBody RegisterRequest request) {
        OtpDispatchDto result = authService.register(request);
        String msg = "DEV_CONSOLE".equals(result.getDeliveryMode())
                ? "Registration successful. Mail is in dev mode; use the OTP shown below."
                : "Registration successful. Please verify your email.";
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(msg, result));
    }

    @PostMapping("/verify-email")
    public ApiResponse<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        authService.verifyEmail(request);
        return ApiResponse.okMessage("Email verified successfully.");
    }

    @PostMapping("/resend-otp")
    public ApiResponse<OtpDispatchDto> resendOtp(@Valid @RequestBody ResendOtpRequest request) {
        OtpDispatchDto result = authService.resendVerificationOtp(request);
        String msg = "DEV_CONSOLE".equals(result.getDeliveryMode())
                ? "OTP generated (dev mode)."
                : "OTP sent successfully.";
        return ApiResponse.ok(msg, result);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        String userAgent = httpRequest.getHeader("User-Agent");
        String ipAddress = httpRequest.getRemoteAddr();
        AuthResponse response = authService.login(request, userAgent, ipAddress);
        return ApiResponse.ok("Login successful.", response);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ApiResponse.ok(response);
    }

    @PostMapping("/forgot-password")
    public ApiResponse<OtpDispatchDto> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        OtpDispatchDto result = authService.forgotPassword(request);
        String msg = "DEV_CONSOLE".equals(result.getDeliveryMode())
                ? "Password reset OTP generated (dev mode)."
                : "Password reset OTP sent.";
        return ApiResponse.ok(msg, result);
    }

    @PostMapping("/verify-reset-otp")
    public ApiResponse<Void> verifyResetOtp(@Valid @RequestBody VerifyResetOtpRequest request) {
        authService.verifyResetOtp(request);
        return ApiResponse.okMessage("OTP verified.");
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ApiResponse.okMessage("Password updated successfully.");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(authorizationHeader);
        return ApiResponse.okMessage("Logged out successfully.");
    }
}
