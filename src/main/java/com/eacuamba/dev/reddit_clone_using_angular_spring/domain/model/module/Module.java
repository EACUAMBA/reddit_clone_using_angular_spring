package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.module;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.permission.Permission;
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
        name = "module",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}),
                @UniqueConstraint(columnNames = {"code"})
        }
)
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String description;

    @OneToMany(mappedBy = "module")
    @Builder.Default
    @ToString.Exclude
    private List<Permission> permissionList = new ArrayList<>();
}
