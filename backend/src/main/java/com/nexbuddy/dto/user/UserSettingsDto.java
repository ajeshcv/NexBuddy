package com.nexbuddy.dto.user;

public class UserSettingsDto {
    private boolean notificationEnabled;
    private boolean profileVisible;
    private boolean locationVisible;
    private String friendRequestPermission;
    private String messagePermission;
    private String language;
    private String theme;

    public UserSettingsDto() {
    }

    public UserSettingsDto(
            boolean notificationEnabled,
            boolean profileVisible,
            boolean locationVisible,
            String friendRequestPermission,
            String messagePermission,
            String language,
            String theme
    ) {
        this.notificationEnabled = notificationEnabled;
        this.profileVisible = profileVisible;
        this.locationVisible = locationVisible;
        this.friendRequestPermission = friendRequestPermission;
        this.messagePermission = messagePermission;
        this.language = language;
        this.theme = theme;
    }

    public boolean isNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
    public boolean isProfileVisible() { return profileVisible; }
    public void setProfileVisible(boolean profileVisible) { this.profileVisible = profileVisible; }
    public boolean isLocationVisible() { return locationVisible; }
    public void setLocationVisible(boolean locationVisible) { this.locationVisible = locationVisible; }
    public String getFriendRequestPermission() { return friendRequestPermission; }
    public void setFriendRequestPermission(String friendRequestPermission) { this.friendRequestPermission = friendRequestPermission; }
    public String getMessagePermission() { return messagePermission; }
    public void setMessagePermission(String messagePermission) { this.messagePermission = messagePermission; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}
