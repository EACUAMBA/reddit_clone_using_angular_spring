package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.ExceptionBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.UserGroup;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.user_permission_user_group.UserUserGroupPermission;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserUserGroupPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserUserGroupPermissionRepository userUserGroupPermissionRepository;
    private final ExceptionBuilder exceptionBuilder;

    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("User with this username '%s' was not found!", username)));
        List<UserUserGroupPermission> userUserGroupPermissionList = userUserGroupPermissionRepository.findAllByUser(user);
        List<GrantedAuthority> authorities = this.getAuthorities(userUserGroupPermissionList);
        user.setGrantedAuthorityList(authorities);
        return user;
    }

    private List<GrantedAuthority> getAuthorities(Collection<UserUserGroupPermission> userUserGroupPermissionCollection) {
        return this.turnCodesIntoGrantedAuthorities(this.getAuthorityCodes(userUserGroupPermissionCollection));
    }

    private Collection<String> getAuthorityCodes(Collection<UserUserGroupPermission> userUserGroupPermissionCollection) {
        Set<String> codes = new HashSet<>();
        for (UserUserGroupPermission userUserGroupPermission : userUserGroupPermissionCollection) {

            UserGroup userGroup = userUserGroupPermission.getUserGroup();
            if (nonNull(userGroup ))
                codes.add(userGroup.getCode());

            Permission permission = userUserGroupPermission.getPermission();
            if (nonNull(permission )) {
                codes.add(permission.getCode());
                if (nonNull(permission.getModule()))
                    codes.add(permission.getModule().getCode());
            }
        }
        return new ArrayList<>(codes);
    }

    private List<GrantedAuthority> turnCodesIntoGrantedAuthorities(Collection<String> codes) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String code : codes) {
            authorities.add(new SimpleGrantedAuthority(code));
        }
        return new ArrayList<>(new HashSet<>(authorities));
    }

    public User update(User user) {
        return this.userRepository.save(user);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> optionalUser;
        if (isNull(id))
            optionalUser = Optional.empty();
        else
            optionalUser = this.userRepository.findById(id);
        return optionalUser.orElseThrow(() -> this.exceptionBuilder.buildEntityNotFoundById("User", id));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> optionalUser;
        if (isNull(username))
            optionalUser = Optional.empty();
        else
            optionalUser = this.userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> this.exceptionBuilder.buildEntityNotFoundBy("username", "User", username));
    }
}
