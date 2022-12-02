package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.user_permission_user_group;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.UserGroup;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_user_group_permission")
public class UserUserGroupPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "user_group_id", referencedColumnName = "id")
    private UserGroup userGroup;
}
