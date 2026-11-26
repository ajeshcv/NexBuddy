package com.nexbuddy.repository;

import com.nexbuddy.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    Optional<UserSession> findByRefreshTokenAndActiveTrue(String refreshToken);
    Optional<UserSession> findByJwtTokenAndActiveTrue(String jwtToken);
    List<UserSession> findByUserUserIdAndActiveTrue(UUID userId);
}
