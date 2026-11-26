package com.nexbuddy.repository;

import com.nexbuddy.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUserUserId(UUID userId);
    Optional<UserProfile> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
