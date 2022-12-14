package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.UserGroup;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.module.Module;
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
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}),
                @UniqueConstraint(columnNames = {"code"})
        }
)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String description;

    @ManyToMany(mappedBy = "permissionList")
    @Builder.Default
    @ToString.Exclude
    private List<UserGroup> userGroupList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Module module;

    @OneToMany(mappedBy = "permission")
    @Builder.Default
    @ToString.Exclude
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();
}
