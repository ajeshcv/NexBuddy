package com.nexbuddy.service;

import com.nexbuddy.config.AppProperties;
import com.nexbuddy.dto.auth.OtpDispatchDto;
import com.nexbuddy.dto.auth.RegisterRequest;
import com.nexbuddy.dto.auth.VerifyEmailRequest;
import com.nexbuddy.entity.*;
import com.nexbuddy.exception.BadRequestException;
import com.nexbuddy.exception.ConflictException;
import com.nexbuddy.repository.*;
import com.nexbuddy.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private Map<String, User> usersByEmail;
    private Map<String, UserProfile> profilesByUsername;
    private List<OtpToken> otpTokens;
    private List<String> sentOtps;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        usersByEmail = new HashMap<>();
        profilesByUsername = new HashMap<>();
        otpTokens = new ArrayList<>();
        sentOtps = new ArrayList<>();

        AppProperties properties = new AppProperties();
        properties.getJwt().setSecret("TmV4QnVkZHlTdXBlclNlY3JldEtleUZvckpXVFRva2VuU2lnbmluZzIwMjYhIQ==");

        UserRepository userRepo = proxy(UserRepository.class, (method, args) -> switch (method) {
            case "existsByEmailIgnoreCase" -> usersByEmail.containsKey(((String) args[0]).toLowerCase());
            case "findByEmailIgnoreCase" -> Optional.ofNullable(usersByEmail.get(((String) args[0]).toLowerCase()));
            case "save" -> {
                User u = (User) args[0];
                if (u.getUserId() == null) u.setUserId(UUID.randomUUID());
                usersByEmail.put(u.getEmail().toLowerCase(), u);
                yield u;
            }
            default -> null;
        });

        UserProfileRepository profileRepo = proxy(UserProfileRepository.class, (method, args) -> switch (method) {
            case "existsByUsernameIgnoreCase" -> profilesByUsername.containsKey(((String) args[0]).toLowerCase());
            case "save" -> {
                UserProfile p = (UserProfile) args[0];
                if (p.getProfileId() == null) p.setProfileId(UUID.randomUUID());
                profilesByUsername.put(p.getUsername().toLowerCase(), p);
                yield p;
            }
            default -> null;
        });

        UserSettingsRepository settingsRepo = proxy(UserSettingsRepository.class, (method, args) -> args != null && args.length > 0 ? args[0] : null);
        UserSessionRepository sessionRepo = proxy(UserSessionRepository.class, (method, args) -> List.of());

        OtpTokenRepository otpRepo = proxy(OtpTokenRepository.class, (method, args) -> switch (method) {
            case "findByUserUserIdAndPurposeAndUsedFalse" -> otpTokens.stream()
                    .filter(t -> t.getUser().getUserId().equals(args[0]) && t.getPurpose() == args[1] && !t.isUsed())
                    .toList();
            case "findTopByUserUserIdAndPurposeOrderByCreatedAtDesc" -> otpTokens.stream()
                    .filter(t -> t.getUser().getUserId().equals(args[0]) && t.getPurpose() == args[1])
                    .reduce((first, second) -> second);
            case "save" -> {
                OtpToken t = (OtpToken) args[0];
                if (t.getOtpId() == null) {
                    t.setOtpId(UUID.randomUUID());
                    otpTokens.add(t);
                }
                yield t;
            }
            case "saveAll" -> args[0];
            default -> null;
        });

        EmailService emailService = new EmailService(properties, null) {
            @Override
            public void sendOtpEmail(String toEmail, String otpCode, String subject, String bodyPrefix) {
                sentOtps.add(otpCode);
            }
        };

        authService = new AuthService(
                userRepo,
                profileRepo,
                settingsRepo,
                sessionRepo,
                otpRepo,
                new BCryptPasswordEncoder(),
                new JwtService(properties),
                emailService,
                properties
        );
    }

    @Test
    void registerCreatesUserProfileAndSendsOtp() {
        RegisterRequest request = validRegisterRequest();

        OtpDispatchDto dispatch = authService.register(request);

        User saved = usersByEmail.get("ajesh@example.com");
        assertNotNull(saved);
        assertFalse(saved.isEmailVerified());
        assertEquals(AccountStatus.PENDING_VERIFICATION, saved.getAccountStatus());
        assertTrue(profilesByUsername.containsKey("ajeshcv"));
        assertEquals(1, sentOtps.size());
        assertEquals("DEV_CONSOLE", dispatch.getDeliveryMode());
        assertEquals(sentOtps.get(0), dispatch.getDevOtp());
    }

    @Test
    void registerRejectsMismatchedPasswords() {
        RegisterRequest request = validRegisterRequest();
        request.setConfirmPassword("Mismatch@123");

        assertThrows(BadRequestException.class, () -> authService.register(request));
    }

    @Test
    void registerRejectsDuplicateEmail() {
        authService.register(validRegisterRequest());

        assertThrows(ConflictException.class, () -> authService.register(validRegisterRequest()));
    }

    @Test
    void verifyEmailActivatesUser() {
        authService.register(validRegisterRequest());
        String otp = sentOtps.get(0);

        VerifyEmailRequest verifyRequest = new VerifyEmailRequest();
        verifyRequest.setEmail("ajesh@example.com");
        verifyRequest.setOtp(otp);

        authService.verifyEmail(verifyRequest);

        User verified = usersByEmail.get("ajesh@example.com");
        assertTrue(verified.isEmailVerified());
        assertEquals(AccountStatus.ACTIVE, verified.getAccountStatus());
    }

    @Test
    void verifyEmailRejectsWrongOtp() {
        authService.register(validRegisterRequest());
        otpTokens.get(0).setExpiresAt(LocalDateTime.now().plusMinutes(10));

        VerifyEmailRequest verifyRequest = new VerifyEmailRequest();
        verifyRequest.setEmail("ajesh@example.com");
        verifyRequest.setOtp("000000");

        assertThrows(BadRequestException.class, () -> authService.verifyEmail(verifyRequest));
    }

    private static RegisterRequest validRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Ajesh");
        request.setLastName("CV");
        request.setUsername("ajeshcv");
        request.setEmail("ajesh@example.com");
        request.setPassword("Password@123");
        request.setConfirmPassword("Password@123");
        return request;
    }

    @FunctionalInterface
    interface RepoHandler {
        Object handle(String methodName, Object[] args);
    }

    @SuppressWarnings("unchecked")
    private static <T> T proxy(Class<T> iface, RepoHandler handler) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[]{iface},
                (proxy, method, args) -> handler.handle(method.getName(), args)
        );
    }
}
