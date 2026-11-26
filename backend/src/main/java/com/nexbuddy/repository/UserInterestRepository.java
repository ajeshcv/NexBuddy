package com.nexbuddy.repository;

import com.nexbuddy.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserInterestRepository extends JpaRepository<UserInterest, UUID> {
    @Query("SELECT ui FROM UserInterest ui JOIN FETCH ui.interest WHERE ui.user.userId = :userId")
    List<UserInterest> findAllWithInterestByUserId(@Param("userId") UUID userId);

    void deleteByUserUserId(UUID userId);
}
