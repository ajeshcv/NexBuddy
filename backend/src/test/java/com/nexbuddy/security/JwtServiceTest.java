package com.nexbuddy.security;

import com.nexbuddy.config.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        AppProperties properties = new AppProperties();
        properties.getJwt().setSecret("TmV4QnVkZHlTdXBlclNlY3JldEtleUZvckpXVFRva2VuU2lnbmluZzIwMjYhIQ==");
        properties.getJwt().setAccessExpirationMs(3600000L);
        properties.getJwt().setRefreshExpirationMs(604800000L);
        jwtService = new JwtService(properties);
    }

    @Test
    void generateAndValidateAccessToken() {
        UUID userId = UUID.randomUUID();
        String token = jwtService.generateAccessToken(userId, "ajesh@example.com", "USER");

        assertNotNull(token);
        assertEquals("ajesh@example.com", jwtService.extractEmail(token));
        assertEquals("ACCESS", jwtService.extractTokenType(token));
        assertTrue(jwtService.isTokenValid(token, "ajesh@example.com"));
        assertFalse(jwtService.isTokenValid(token, "other@example.com"));
        assertFalse(jwtService.isRefreshTokenValid(token));
    }

    @Test
    void generateAndValidateRefreshToken() {
        UUID userId = UUID.randomUUID();
        String refreshToken = jwtService.generateRefreshToken(userId, "ajesh@example.com");

        assertNotNull(refreshToken);
        assertEquals("REFRESH", jwtService.extractTokenType(refreshToken));
        assertTrue(jwtService.isRefreshTokenValid(refreshToken));
    }
}
