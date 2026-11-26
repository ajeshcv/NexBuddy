package com.nexbuddy.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_interests", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_interest", columnNames = {"user_id", "interest_id"})
})
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_interest_id", nullable = false, updatable = false)
    private UUID userInterestId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserInterest() {
    }

    public UserInterest(User user, Interest interest) {
        this.user = user;
        this.interest = interest;
    }

    public UUID getUserInterestId() { return userInterestId; }
    public void setUserInterestId(UUID userInterestId) { this.userInterestId = userInterestId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Interest getInterest() { return interest; }
    public void setInterest(Interest interest) { this.interest = interest; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
