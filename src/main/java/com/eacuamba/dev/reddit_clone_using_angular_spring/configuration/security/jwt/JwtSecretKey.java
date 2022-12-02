package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class JwtSecretKey {
    private final JwtConfiguration jwtConfiguration;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(this.jwtConfiguration.getSecretKey().getBytes());
    }
}
