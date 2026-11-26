package com.nexbuddy.service;

import com.nexbuddy.config.AppProperties;
import com.nexbuddy.dto.auth.*;
import com.nexbuddy.entity.*;
import com.nexbuddy.exception.*;
import com.nexbuddy.repository.*;
import com.nexbuddy.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final UserSessionRepository userSessionRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AppProperties appProperties;

    public AuthService(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            UserSettingsRepository userSettingsRepository,
            UserSessionRepository userSessionRepository,
            OtpTokenRepository otpTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EmailService emailService,
            AppProperties appProperties
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.userSessionRepository = userSessionRepository;
        this.otpTokenRepository = otpTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.appProperties = appProperties;
    }

    @Transactional
    public OtpDispatchDto register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password do not match");
        }
        String normalizedEmail = request.getEmail().trim().toLowerCase();
        String normalizedUsername = request.getUsername().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email already exists");
        }
        if (userProfileRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new ConflictException("Username already exists");
        }

        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailVerified(false);
        user.setAccountStatus(AccountStatus.PENDING_VERIFICATION);
        user.setRole(Role.USER);
        user = userRepository.save(user);

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setFirstName(request.getFirstName().trim());
        profile.setLastName(request.getLastName().trim());
        profile.setUsername(normalizedUsername);
        userProfileRepository.save(profile);

        UserSettings settings = new UserSettings();
        settings.setUser(user);
        userSettingsRepository.save(settings);

        String otp = issueOtp(user, OtpPurpose.EMAIL_VERIFICATION);
        emailService.sendOtpEmail(
                user.getEmail(),
                otp,
                "Verify your NexBuddy email",
                "Welcome to NexBuddy! Please verify your email address."
        );
        return buildOtpDispatch(user.getEmail(), otp);
    }

    @Transactional
    public void verifyEmail(VerifyEmailRequest request) {
        User user = findUserByEmailOrThrow(request.getEmail());
        if (user.isEmailVerified()) {
            throw new BadRequestException("Email is already verified");
        }

        OtpToken token = validateOtp(user, OtpPurpose.EMAIL_VERIFICATION, request.getOtp().trim());
        token.setVerified(true);
        token.setUsed(true);
        otpTokenRepository.save(token);

        user.setEmailVerified(true);
        if (user.getAccountStatus() == AccountStatus.PENDING_VERIFICATION) {
            user.setAccountStatus(AccountStatus.ACTIVE);
        }
        userRepository.save(user);
    }

    @Transactional
    public OtpDispatchDto resendVerificationOtp(ResendOtpRequest request) {
        User user = findUserByEmailOrThrow(request.getEmail());
        if (user.isEmailVerified()) {
            throw new BadRequestException("Email is already verified");
        }

        String otp = issueOtp(user, OtpPurpose.EMAIL_VERIFICATION);
        emailService.sendOtpEmail(
                user.getEmail(),
                otp,
                "NexBuddy Email Verification OTP",
                "Here is your new email verification OTP."
        );
        return buildOtpDispatch(user.getEmail(), otp);
    }

    @Transactional
    public AuthResponse login(LoginRequest request, String device, String ipAddress) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (user.getAccountStatus() == AccountStatus.DELETED) {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (user.getAccountStatus() == AccountStatus.SUSPENDED) {
            throw new ApiException(HttpStatus.LOCKED, "Account Suspended");
        }
        if (!user.isEmailVerified()) {
            throw new ForbiddenException("Email Not Verified");
        }

        UserProfile profile = userProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        String accessToken = jwtService.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getUserId(), user.getEmail());

        UserSession session = new UserSession();
        session.setUser(user);
        session.setJwtToken(accessToken);
        session.setRefreshToken(refreshToken);
        session.setDevice(device != null ? device : "Unknown");
        session.setIpAddress(ipAddress != null ? ipAddress : "Unknown");
        session.setActive(true);
        userSessionRepository.save(session);

        UserSummaryDto summary = new UserSummaryDto(
                user.getUserId(),
                profile.getFirstName() + " " + profile.getLastName(),
                profile.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(
                accessToken,
                refreshToken,
                appProperties.getJwt().getAccessExpirationMs() / 1000L,
                summary
        );
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken().trim();
        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }

        UserSession session = userSessionRepository.findByRefreshTokenAndActiveTrue(refreshToken)
                .orElseThrow(() -> new UnauthorizedException("Session is inactive or expired"));

        User user = session.getUser();
        if (user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new ForbiddenException("Account is not active");
        }

        String newAccessToken = jwtService.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole().name());
        session.setJwtToken(newAccessToken);
        userSessionRepository.save(session);

        return new AuthResponse(
                newAccessToken,
                null,
                appProperties.getJwt().getAccessExpirationMs() / 1000L,
                null
        );
    }

    @Transactional
    public OtpDispatchDto forgotPassword(ForgotPasswordRequest request) {
        User user = findUserByEmailOrThrow(request.getEmail());
        if (user.getAccountStatus() == AccountStatus.DELETED) {
            throw new ResourceNotFoundException("User not found");
        }

        String otp = issueOtp(user, OtpPurpose.PASSWORD_RESET);
        emailService.sendOtpEmail(
                user.getEmail(),
                otp,
                "NexBuddy Password Reset OTP",
                "Use the OTP below to reset your NexBuddy password."
        );
        return buildOtpDispatch(user.getEmail(), otp);
    }

    @Transactional
    public void verifyResetOtp(VerifyResetOtpRequest request) {
        User user = findUserByEmailOrThrow(request.getEmail());
        OtpToken token = validateOtp(user, OtpPurpose.PASSWORD_RESET, request.getOtp().trim());
        token.setVerified(true);
        otpTokenRepository.save(token);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("New password and confirm password do not match");
        }
        User user = findUserByEmailOrThrow(request.getEmail());

        OtpToken token = otpTokenRepository
                .findTopByUserUserIdAndPurposeOrderByCreatedAtDesc(user.getUserId(), OtpPurpose.PASSWORD_RESET)
                .orElseThrow(() -> new BadRequestException("No password reset OTP found"));

        if (token.isUsed() || !token.isVerified() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Password reset OTP is not verified or has expired");
        }

        token.setUsed(true);
        otpTokenRepository.save(token);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        invalidateAllSessions(user.getUserId());
    }

    @Transactional
    public void logout(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authentication Required");
        }
        String jwt = authorizationHeader.substring(7).trim();
        userSessionRepository.findByJwtTokenAndActiveTrue(jwt).ifPresent(session -> {
            session.setActive(false);
            session.setLogoutTime(LocalDateTime.now());
            userSessionRepository.save(session);
        });
    }

    private OtpDispatchDto buildOtpDispatch(String email, String otp) {
        if (emailService.isMailEnabled()) {
            return new OtpDispatchDto(email, "EMAIL", null);
        }
        return new OtpDispatchDto(email, "DEV_CONSOLE", otp);
    }

    private User findUserByEmailOrThrow(String email) {
        return userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    private String issueOtp(User user, OtpPurpose purpose) {
        List<OtpToken> unused = otpTokenRepository.findByUserUserIdAndPurposeAndUsedFalse(user.getUserId(), purpose);
        unused.forEach(t -> t.setUsed(true));
        otpTokenRepository.saveAll(unused);

        String otpCode = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
        OtpToken token = new OtpToken();
        token.setUser(user);
        token.setOtpCode(otpCode);
        token.setPurpose(purpose);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(appProperties.getOtp().getExpirationMinutes()));
        token.setVerified(false);
        token.setUsed(false);
        otpTokenRepository.save(token);
        return otpCode;
    }

    private OtpToken validateOtp(User user, OtpPurpose purpose, String otpCode) {
        OtpToken token = otpTokenRepository
                .findTopByUserUserIdAndPurposeOrderByCreatedAtDesc(user.getUserId(), purpose)
                .orElseThrow(() -> new BadRequestException("Invalid or expired OTP"));

        if (token.isUsed() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP has expired. Please request a new one.");
        }
        if (!token.getOtpCode().equals(otpCode)) {
            throw new BadRequestException("Invalid OTP code");
        }
        return token;
    }

    private void invalidateAllSessions(UUID userId) {
        List<UserSession> activeSessions = userSessionRepository.findByUserUserIdAndActiveTrue(userId);
        LocalDateTime now = LocalDateTime.now();
        activeSessions.forEach(s -> {
            s.setActive(false);
            s.setLogoutTime(now);
        });
        userSessionRepository.saveAll(activeSessions);
    }
}
