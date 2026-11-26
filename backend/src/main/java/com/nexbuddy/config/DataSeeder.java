package com.nexbuddy.config;

import com.nexbuddy.entity.*;
import com.nexbuddy.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    public DataSeeder(
            InterestRepository interestRepository,
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            UserSettingsRepository userSettingsRepository,
            PasswordEncoder passwordEncoder,
            AppProperties appProperties
    ) {
        this.interestRepository = interestRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
    }

    @Override
    public void run(String... args) {
        seedInterests();
        seedDefaultAdmin();
    }

    private void seedInterests() {
        List<Interest> defaults = List.of(
                new Interest("Travel", "Lifestyle", "flight"),
                new Interest("Study", "Education", "menu_book"),
                new Interest("Gaming", "Entertainment", "sports_esports"),
                new Interest("Movies", "Entertainment", "movie"),
                new Interest("Food", "Lifestyle", "restaurant"),
                new Interest("Tea & Coffee", "Lifestyle", "local_cafe"),
                new Interest("Football", "Sports", "sports_soccer"),
                new Interest("Cricket", "Sports", "sports_cricket"),
                new Interest("Fitness & Gym", "Fitness", "fitness_center"),
                new Interest("Trekking", "Outdoors", "terrain"),
                new Interest("Photography", "Hobbies", "photo_camera"),
                new Interest("Music", "Hobbies", "music_note"),
                new Interest("Reading", "Hobbies", "auto_stories"),
                new Interest("Programming", "Technology", "code"),
                new Interest("Networking", "Career", "work")
        );

        int added = 0;
        for (Interest interest : defaults) {
            if (!interestRepository.existsByInterestNameIgnoreCase(interest.getInterestName())) {
                interestRepository.save(interest);
                added++;
            }
        }
        if (added > 0) {
            log.info("Seeded {} default interests.", added);
        }
    }

    private void seedDefaultAdmin() {
        String adminEmail = appProperties.getAdmin().getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(adminEmail)) {
            return;
        }

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(appProperties.getAdmin().getPassword()));
        admin.setEmailVerified(true);
        admin.setAccountStatus(AccountStatus.ACTIVE);
        admin.setRole(Role.ADMIN);
        admin = userRepository.save(admin);

        UserProfile profile = new UserProfile();
        profile.setUser(admin);
        profile.setFirstName(appProperties.getAdmin().getFirstName());
        profile.setLastName(appProperties.getAdmin().getLastName());
        profile.setUsername(appProperties.getAdmin().getUsername());
        profile.setBio("Platform Administrator");
        userProfileRepository.save(profile);

        UserSettings settings = new UserSettings();
        settings.setUser(admin);
        userSettingsRepository.save(settings);

        log.info("Seeded default ADMIN user: {}", adminEmail);
    }
}
