package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.ValidationToken;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationTokenRepository extends JpaRepository<ValidationToken, Long> {
}