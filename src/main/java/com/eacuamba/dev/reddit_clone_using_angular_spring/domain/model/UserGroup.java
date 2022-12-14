package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.user_permission_user_group.UserUserGroupPermission;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "user_group",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}),
                @UniqueConstraint(columnNames = {"code"})
        }
)
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String description;

    @ManyToMany()
    @JoinTable(
            name = "user_group_permission",
            joinColumns = {@JoinColumn(name = "user_group_id", referencedColumnName = "id", table = "user_group")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id", table = "permission")}
    )
    @Builder.Default
    @ToString.Exclude
    List<Permission> permissionList = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup")
    @Builder.Default
    @ToString.Exclude
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();
}
