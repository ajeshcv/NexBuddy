package com.nexbuddy.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interests", indexes = {
        @Index(name = "idx_interests_name", columnList = "interest_name", unique = true)
})
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interest_id", nullable = false, updatable = false)
    private UUID interestId;

    @Column(name = "interest_name", nullable = false, unique = true, length = 80)
    private String interestName;

    @Column(name = "category", nullable = false, length = 60)
    private String category;

    @Column(name = "icon", length = 60)
    private String icon;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Interest() {
    }

    public Interest(String interestName, String category, String icon) {
        this.interestName = interestName;
        this.category = category;
        this.icon = icon;
    }

    public UUID getInterestId() { return interestId; }
    public void setInterestId(UUID interestId) { this.interestId = interestId; }
    public String getInterestName() { return interestName; }
    public void setInterestName(String interestName) { this.interestName = interestName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
