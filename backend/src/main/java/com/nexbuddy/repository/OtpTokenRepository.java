package com.nexbuddy.repository;

import com.nexbuddy.entity.OtpPurpose;
import com.nexbuddy.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OtpTokenRepository extends JpaRepository<OtpToken, UUID> {
    Optional<OtpToken> findTopByUserUserIdAndPurposeOrderByCreatedAtDesc(UUID userId, OtpPurpose purpose);
    List<OtpToken> findByUserUserIdAndPurposeAndUsedFalse(UUID userId, OtpPurpose purpose);
}
