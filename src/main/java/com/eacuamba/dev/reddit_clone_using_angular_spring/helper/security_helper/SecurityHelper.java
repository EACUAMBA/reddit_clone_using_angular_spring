package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.security_helper;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityHelper {
    private final UserService userService;

    public Optional<User> getAutheticatedUser() {
        if (!this.isAuthenticated())
            return Optional.empty();

        Authentication authentication = this.getAuthentication();
        User user = userService.loadUserByUsername((String) authentication.getPrincipal());
        return Optional.ofNullable(user);
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Boolean isAuthenticated() {
        Authentication authentication;
        if (nonNull(authentication = this.getAuthentication()))
            return authentication.isAuthenticated();
        return Boolean.FALSE;
    }

    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public Authentication getAuthentication() {
        return this.getSecurityContext().getAuthentication();
    }

}
