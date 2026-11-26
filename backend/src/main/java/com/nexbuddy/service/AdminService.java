package com.nexbuddy.service;

import com.nexbuddy.dto.admin.AdminDashboardDto;
import com.nexbuddy.dto.admin.AdminUserDto;
import com.nexbuddy.entity.AccountStatus;
import com.nexbuddy.entity.User;
import com.nexbuddy.entity.UserProfile;
import com.nexbuddy.entity.UserSession;
import com.nexbuddy.exception.BadRequestException;
import com.nexbuddy.exception.ResourceNotFoundException;
import com.nexbuddy.repository.InterestRepository;
import com.nexbuddy.repository.UserProfileRepository;
import com.nexbuddy.repository.UserRepository;
import com.nexbuddy.repository.UserSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final InterestRepository interestRepository;
    private final UserSessionRepository userSessionRepository;

    public AdminService(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            InterestRepository interestRepository,
            UserSessionRepository userSessionRepository
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.interestRepository = interestRepository;
        this.userSessionRepository = userSessionRepository;
    }

    @Transactional(readOnly = true)
    public AdminDashboardDto getDashboardStats() {
        List<User> allUsers = userRepository.findAll().stream()
                .filter(u -> u.getAccountStatus() != AccountStatus.DELETED)
                .toList();

        long active = allUsers.stream().filter(u -> u.getAccountStatus() == AccountStatus.ACTIVE).count();
        long pending = allUsers.stream().filter(u -> u.getAccountStatus() == AccountStatus.PENDING_VERIFICATION).count();
        long suspended = allUsers.stream().filter(u -> u.getAccountStatus() == AccountStatus.SUSPENDED).count();

        return new AdminDashboardDto(
                allUsers.size(),
                active,
                pending,
                suspended,
                interestRepository.count()
        );
    }

    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getAccountStatus() != AccountStatus.DELETED)
                .map(user -> {
                    UserProfile profile = userProfileRepository.findByUserUserId(user.getUserId()).orElse(null);
                    return new AdminUserDto(
                            user.getUserId(),
                            profile != null ? profile.getFirstName() : "",
                            profile != null ? profile.getLastName() : "",
                            profile != null ? profile.getUsername() : "",
                            user.getEmail(),
                            user.getRole().name(),
                            user.getAccountStatus().name(),
                            user.isEmailVerified(),
                            user.getCreatedAt()
                    );
                })
                .toList();
    }

    @Transactional
    public void suspendUser(UUID targetUserId, UUID adminUserId) {
        if (targetUserId.equals(adminUserId)) {
            throw new BadRequestException("Admin cannot suspend their own account");
        }
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.SUSPENDED);
        userRepository.save(user);

        List<UserSession> sessions = userSessionRepository.findByUserUserIdAndActiveTrue(targetUserId);
        LocalDateTime now = LocalDateTime.now();
        sessions.forEach(s -> {
            s.setActive(false);
            s.setLogoutTime(now);
        });
        userSessionRepository.saveAll(sessions);
    }

    @Transactional
    public void activateUser(UUID targetUserId) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setEmailVerified(true);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
    }
}
