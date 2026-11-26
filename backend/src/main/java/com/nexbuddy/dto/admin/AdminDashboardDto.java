package com.nexbuddy.dto.admin;

public class AdminDashboardDto {
    private long totalUsers;
    private long activeUsers;
    private long pendingVerificationUsers;
    private long suspendedUsers;
    private long totalInterests;

    public AdminDashboardDto() {
    }

    public AdminDashboardDto(
            long totalUsers,
            long activeUsers,
            long pendingVerificationUsers,
            long suspendedUsers,
            long totalInterests
    ) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.pendingVerificationUsers = pendingVerificationUsers;
        this.suspendedUsers = suspendedUsers;
        this.totalInterests = totalInterests;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
    public long getPendingVerificationUsers() { return pendingVerificationUsers; }
    public void setPendingVerificationUsers(long pendingVerificationUsers) { this.pendingVerificationUsers = pendingVerificationUsers; }
    public long getSuspendedUsers() { return suspendedUsers; }
    public void setSuspendedUsers(long suspendedUsers) { this.suspendedUsers = suspendedUsers; }
    public long getTotalInterests() { return totalInterests; }
    public void setTotalInterests(long totalInterests) { this.totalInterests = totalInterests; }
}
