package com.loyayz.simple.auth.security;

import com.loyayz.simple.auth.core.authentication.AuthCredentials;
import com.loyayz.simple.auth.core.identity.AuthIdentity;
import com.loyayz.simple.auth.core.identity.AuthIdentityRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@SuppressWarnings("unchecked")
public class SecurityContextHelper {

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static Optional<SecurityToken> getAuthentication() {
        Authentication authentication = getSecurityContext().getAuthentication();
        if (!(authentication instanceof SecurityToken)) {
            authentication = null;
        }
        return Optional.ofNullable((SecurityToken) authentication);
    }

    /**
     * 获取当前用户
     */
    public static <T extends AuthIdentity> T getUser() {
        return getAuthentication()
                .map(token -> (T) token.getPrincipal())
                .orElse(null);
    }

    /**
     * 当前用户请求携带的 token
     */
    public static <T extends AuthCredentials> T getCredentials() {
        return getAuthentication()
                .map(token -> (T) token.getCredentials())
                .orElse(null);
    }

    /**
     * 当前用户请求携带的 token
     */
    public static String getToken() {
        AuthCredentials credentials = getCredentials();
        return credentials == null ? null : credentials.getToken();
    }

    /**
     * 获取当前用户的id
     */
    public static String getUserId() {
        AuthIdentity user = getUser();
        return user == null ? null : user.getId();
    }

    /**
     * 获取当前用户名
     */
    public static String getUserName() {
        AuthIdentity user = getUser();
        return user == null ? null : user.getName();
    }

    /**
     * 获取当前用户的角色
     */
    public static List<String> getUserRoleNames() {
        return getUserRoles().stream()
                .map(AuthIdentityRole::getName)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前用户的角色
     */
    public static List<String> getUserRoleCodes() {
        return getUserRoles().stream()
                .map(AuthIdentityRole::getCode)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前用户的角色
     */
    public static List<AuthIdentityRole> getUserRoles() {
        AuthIdentity user = getUser();
        return user == null ? Collections.emptyList() : user.getRoles();
    }

}
