package com.nexbuddy.util;

import com.nexbuddy.exception.UnauthorizedException;
import com.nexbuddy.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Authentication Required");
        }
        return principal;
    }

    public static UUID getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}
