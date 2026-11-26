package com.nexbuddy.repository;

import com.nexbuddy.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
    Optional<Interest> findByInterestNameIgnoreCase(String interestName);
    boolean existsByInterestNameIgnoreCase(String interestName);
    List<Interest> findAllByOrderByCategoryAscInterestNameAsc();
}
