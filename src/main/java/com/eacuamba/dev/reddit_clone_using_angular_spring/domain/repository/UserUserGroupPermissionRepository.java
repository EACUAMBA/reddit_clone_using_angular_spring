package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.UserGroup;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.user_permission_user_group.UserUserGroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserUserGroupPermissionRepository extends JpaRepository<UserUserGroupPermission, Long> {
    List<UserUserGroupPermission> findAllByUser(User user);
    List<UserUserGroupPermission> findAllByUserGroup(UserGroup userGroup);
    List<UserUserGroupPermission> findAllByPermission(Permission permission);
}
