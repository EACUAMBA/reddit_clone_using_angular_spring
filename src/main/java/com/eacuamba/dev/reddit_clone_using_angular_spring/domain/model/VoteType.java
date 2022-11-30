package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
public enum VoteType {
    UP(1),
    DOWN(-1);

    private final Integer direction;
}
