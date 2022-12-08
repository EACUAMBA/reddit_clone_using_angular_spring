package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser(User user);
    List<Token> findAllByTokenType(TokenType tokenType);
    Optional<Token> findByValue(String value);
}
