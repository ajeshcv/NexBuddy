package com.nexbuddy.service;

import com.nexbuddy.dto.user.*;
import com.nexbuddy.entity.*;
import com.nexbuddy.exception.*;
import com.nexbuddy.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserSessionRepository userSessionRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            UserSettingsRepository userSettingsRepository,
            InterestRepository interestRepository,
            UserInterestRepository userInterestRepository,
            UserSessionRepository userSessionRepository,
            FileStorageService fileStorageService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
        this.userSessionRepository = userSessionRepository;
        this.fileStorageService = fileStorageService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(UUID userId) {
        User user = findActiveUserOrThrow(userId);
        return buildProfileResponse(user, true);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(UUID targetUserId, UUID requesterId) {
        User user = findActiveUserOrThrow(targetUserId);
        boolean isSelf = targetUserId.equals(requesterId);
        if (!isSelf) {
            UserSettings settings = userSettingsRepository.findByUserUserId(targetUserId).orElse(null);
            if (settings != null && !settings.isProfileVisible()) {
                throw new ForbiddenException("This profile is private");
            }
        }
        return buildProfileResponse(user, isSelf);
    }

    @Transactional
    public UserProfileResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        User user = findActiveUserOrThrow(userId);
        UserProfile profile = userProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        if (request.getFirstName() != null) {
            profile.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null) {
            profile.setLastName(request.getLastName().trim());
        }
        if (request.getUsername() != null) {
            String newUsername = request.getUsername().trim().toLowerCase();
            if (!newUsername.equalsIgnoreCase(profile.getUsername())
                    && userProfileRepository.existsByUsernameIgnoreCase(newUsername)) {
                throw new ConflictException("Username already exists");
            }
            profile.setUsername(newUsername);
        }
        if (request.getBio() != null) profile.setBio(request.getBio().trim());
        if (request.getGender() != null) profile.setGender(request.getGender().trim());
        if (request.getDateOfBirth() != null) profile.setDateOfBirth(request.getDateOfBirth());
        if (request.getPhone() != null) profile.setPhone(request.getPhone().trim());
        if (request.getCity() != null) profile.setCity(request.getCity().trim());
        if (request.getState() != null) profile.setState(request.getState().trim());
        if (request.getCountry() != null) profile.setCountry(request.getCountry().trim());
        if (request.getOccupation() != null) profile.setOccupation(request.getOccupation().trim());
        if (request.getEducation() != null) profile.setEducation(request.getEducation().trim());

        userProfileRepository.save(profile);
        return buildProfileResponse(user, true);
    }

    @Transactional
    public String uploadProfilePicture(UUID userId, MultipartFile file) {
        UserProfile profile = userProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        String url = fileStorageService.storeImage(file, "profiles");
        profile.setProfilePicture(url);
        userProfileRepository.save(profile);
        return url;
    }

    @Transactional
    public String uploadCoverPhoto(UUID userId, MultipartFile file) {
        UserProfile profile = userProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        String url = fileStorageService.storeImage(file, "covers");
        profile.setCoverPhoto(url);
        userProfileRepository.save(profile);
        return url;
    }

    @Transactional
    public List<InterestDto> updateInterests(UUID userId, UpdateInterestsRequest request) {
        User user = findActiveUserOrThrow(userId);
        List<UUID> distinctIds = request.getInterestIds().stream().distinct().toList();
        List<Interest> interests = interestRepository.findAllById(distinctIds);
        if (interests.size() != distinctIds.size()) {
            throw new ResourceNotFoundException("One or more interest IDs were not found");
        }

        userInterestRepository.deleteByUserUserId(userId);
        userInterestRepository.flush();

        List<UserInterest> userInterests = interests.stream()
                .map(interest -> new UserInterest(user, interest))
                .toList();
        userInterestRepository.saveAll(userInterests);

        return interests.stream().map(InterestService::toDto).toList();
    }

    @Transactional(readOnly = true)
    public UserSettingsDto getSettings(UUID userId) {
        UserSettings settings = userSettingsRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User settings not found"));
        return toSettingsDto(settings);
    }

    @Transactional
    public UserSettingsDto updateSettings(UUID userId, UpdateSettingsRequest request) {
        UserSettings settings = userSettingsRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User settings not found"));

        if (request.getNotificationEnabled() != null) {
            settings.setNotificationEnabled(request.getNotificationEnabled());
        }
        if (request.getProfileVisible() != null) {
            settings.setProfileVisible(request.getProfileVisible());
        }
        if (request.getLocationVisible() != null) {
            settings.setLocationVisible(request.getLocationVisible());
        }
        if (request.getFriendRequestPermission() != null) {
            settings.setFriendRequestPermission(request.getFriendRequestPermission().trim());
        }
        if (request.getMessagePermission() != null) {
            settings.setMessagePermission(request.getMessagePermission().trim());
        }
        if (request.getLanguage() != null) {
            settings.setLanguage(request.getLanguage().trim());
        }
        if (request.getTheme() != null) {
            settings.setTheme(request.getTheme().trim());
        }

        userSettingsRepository.save(settings);
        return toSettingsDto(settings);
    }

    @Transactional
    public void changePassword(UUID userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("New password and confirm password do not match");
        }
        User user = findActiveUserOrThrow(userId);
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteMyAccount(UUID userId) {
        User user = findActiveUserOrThrow(userId);
        user.setAccountStatus(AccountStatus.DELETED);
        userRepository.save(user);

        List<UserSession> sessions = userSessionRepository.findByUserUserIdAndActiveTrue(userId);
        LocalDateTime now = LocalDateTime.now();
        sessions.forEach(s -> {
            s.setActive(false);
            s.setLogoutTime(now);
        });
        userSessionRepository.saveAll(sessions);
    }

    private User findActiveUserOrThrow(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getAccountStatus() == AccountStatus.DELETED) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    private UserProfileResponse buildProfileResponse(User user, boolean includePrivateFields) {
        UserProfile profile = userProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        List<InterestDto> interests = userInterestRepository.findAllWithInterestByUserId(user.getUserId())
                .stream()
                .map(ui -> InterestService.toDto(ui.getInterest()))
                .toList();

        UserSettingsDto settingsDto = null;
        if (includePrivateFields) {
            settingsDto = userSettingsRepository.findByUserUserId(user.getUserId())
                    .map(UserService::toSettingsDto)
                    .orElse(null);
        }

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getUserId());
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
        response.setUsername(profile.getUsername());
        response.setEmail(includePrivateFields ? user.getEmail() : null);
        response.setBio(profile.getBio());
        response.setGender(profile.getGender());
        response.setDateOfBirth(includePrivateFields ? profile.getDateOfBirth() : null);
        response.setPhone(includePrivateFields ? profile.getPhone() : null);
        response.setCity(profile.getCity());
        response.setState(profile.getState());
        response.setCountry(profile.getCountry());
        response.setProfilePicture(profile.getProfilePicture());
        response.setCoverPhoto(profile.getCoverPhoto());
        response.setOccupation(profile.getOccupation());
        response.setEducation(profile.getEducation());
        response.setRole(user.getRole().name());
        response.setEmailVerified(includePrivateFields ? user.isEmailVerified() : null);
        response.setInterests(interests);
        response.setSettings(settingsDto);
        return response;
    }

    private static UserSettingsDto toSettingsDto(UserSettings settings) {
        return new UserSettingsDto(
                settings.isNotificationEnabled(),
                settings.isProfileVisible(),
                settings.isLocationVisible(),
                settings.getFriendRequestPermission(),
                settings.getMessagePermission(),
                settings.getLanguage(),
                settings.getTheme()
        );
    }
}
