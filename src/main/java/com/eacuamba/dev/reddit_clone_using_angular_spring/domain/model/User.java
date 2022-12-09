package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.user_permission_user_group.UserUserGroupPermission;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}),
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @ToString.Exclude
    private List<Token> tokenList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "user_user_group",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", table = "user")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_group_id", referencedColumnName = "id", table = "user_group")
            }
    )
    @Builder.Default
    @ToString.Exclude
    private List<UserGroup> userGroupList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @ToString.Exclude
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();

    @Builder.Default
    private Boolean enabled = Boolean.FALSE;

    @Column(name = "account_non_expired")
    @Builder.Default
    private Boolean accountNonExpired = Boolean.TRUE;

    @Column(name = "account_non_locked")
    @Builder.Default
    private Boolean accountNonLocked = Boolean.TRUE;

    @Column(name = "credentials_non_expired")
    @Builder.Default
    private Boolean credentialsNonExpired = Boolean.TRUE;

    @Builder.Default
    @Column(name = "tries")
    private Integer tries = 5;

    @Builder.Default
    @Transient
    private List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
