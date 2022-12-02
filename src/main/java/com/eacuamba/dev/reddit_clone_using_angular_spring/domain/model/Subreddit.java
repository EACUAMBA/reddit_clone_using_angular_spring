package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table
@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of a subreddit community cannot be empty!")
    private String name;

    @NotBlank(message = "Description is required!")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
    @ToString.Exclude
    private List<Post> postList;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subreddit subreddit = (Subreddit) o;
        return id != null && Objects.equals(id, subreddit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
