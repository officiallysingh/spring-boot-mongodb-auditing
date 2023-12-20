package com.ksoot.mongodb;

import com.ksoot.common.CommonConstants;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;

/**
 * @author Rajveer Singh
 */
public class IdentityHelper {

    private IdentityHelper() {
        throw new IllegalStateException("Just a utility class, not supposed to be instantiated");
    }

    public static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication;
        } else {
            throw new InsufficientAuthenticationException("Authentication required");
        }
    }

    public static Principal getPrinciple() {
        return (Principal) getAuthentication().getPrincipal();
    }

    public static String getPrincipalName() {
        return getAuthentication().getName();
    }

    public static List<String> getAuthorities() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        } else {
            throw new InsufficientAuthenticationException("Authentication required");
        }
    }

    public static String getLoginName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else {
            throw new InsufficientAuthenticationException("Authentication required");
        }
    }

    public static String getAuditUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else {
            return CommonConstants.SYSTEM_USER;
        }
    }

    public enum ClaimName {

        // @formatter:off
    SUBJECT("preferred_username"),
    CLIENT_ID("clientId"),
    CLIENT_NAME("client_name");
    // @formatter:on

        private final String value;

        private ClaimName(final String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}
