package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
