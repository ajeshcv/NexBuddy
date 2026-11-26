package com.nexbuddy.dto.user;

public class UpdateSettingsRequest {
    private Boolean notificationEnabled;
    private Boolean profileVisible;
    private Boolean locationVisible;
    private String friendRequestPermission;
    private String messagePermission;
    private String language;
    private String theme;

    public Boolean getNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(Boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
    public Boolean getProfileVisible() { return profileVisible; }
    public void setProfileVisible(Boolean profileVisible) { this.profileVisible = profileVisible; }
    public Boolean getLocationVisible() { return locationVisible; }
    public void setLocationVisible(Boolean locationVisible) { this.locationVisible = locationVisible; }
    public String getFriendRequestPermission() { return friendRequestPermission; }
    public void setFriendRequestPermission(String friendRequestPermission) { this.friendRequestPermission = friendRequestPermission; }
    public String getMessagePermission() { return messagePermission; }
    public void setMessagePermission(String messagePermission) { this.messagePermission = messagePermission; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}
