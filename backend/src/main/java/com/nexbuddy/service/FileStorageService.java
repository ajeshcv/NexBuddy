package com.nexbuddy.service;

import com.nexbuddy.config.AppProperties;
import com.nexbuddy.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    private final AppProperties appProperties;

    public FileStorageService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String storeImage(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is required");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new BadRequestException("Only JPEG, PNG, WEBP, or GIF images are allowed");
        }

        String originalName = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg");
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < originalName.length() - 1) {
            extension = originalName.substring(dotIndex).replaceAll("[^a-zA-Z0-9.]", "");
        }
        if (extension.isEmpty()) {
            extension = ".jpg";
        }

        String fileName = UUID.randomUUID() + extension;
        try {
            Path rootDir = Paths.get(appProperties.getStorage().getUploadDir()).toAbsolutePath().normalize();
            Path targetDir = rootDir.resolve(folder).normalize();
            if (!targetDir.startsWith(rootDir)) {
                throw new BadRequestException("Invalid storage folder");
            }
            Files.createDirectories(targetDir);
            Path targetFile = targetDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + folder + "/" + fileName;
        } catch (IOException ex) {
            throw new BadRequestException("Failed to store uploaded file: " + ex.getMessage());
        }
    }
}
