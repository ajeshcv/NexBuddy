package com.nexbuddy.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "setting_id", nullable = false, updatable = false)
    private UUID settingId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "profile_visible", nullable = false)
    private boolean profileVisible = true;

    @Column(name = "friend_request_permission", nullable = false, length = 30)
    private String friendRequestPermission = "EVERYONE";

    @Column(name = "message_permission", nullable = false, length = 30)
    private String messagePermission = "EVERYONE";

    @Column(name = "location_visible", nullable = false)
    private boolean locationVisible = true;

    @Column(name = "notification_enabled", nullable = false)
    private boolean notificationEnabled = true;

    @Column(name = "theme", nullable = false, length = 20)
    private String theme = "Light";

    @Column(name = "language", nullable = false, length = 30)
    private String language = "English";

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public UserSettings() {
    }

    public UUID getSettingId() { return settingId; }
    public void setSettingId(UUID settingId) { this.settingId = settingId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public boolean isProfileVisible() { return profileVisible; }
    public void setProfileVisible(boolean profileVisible) { this.profileVisible = profileVisible; }
    public String getFriendRequestPermission() { return friendRequestPermission; }
    public void setFriendRequestPermission(String friendRequestPermission) { this.friendRequestPermission = friendRequestPermission; }
    public String getMessagePermission() { return messagePermission; }
    public void setMessagePermission(String messagePermission) { this.messagePermission = messagePermission; }
    public boolean isLocationVisible() { return locationVisible; }
    public void setLocationVisible(boolean locationVisible) { this.locationVisible = locationVisible; }
    public boolean isNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
