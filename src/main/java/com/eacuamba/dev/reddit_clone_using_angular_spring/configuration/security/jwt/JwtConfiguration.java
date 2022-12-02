package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.jwt-configuration")
public class JwtConfiguration {
    private String  secretKey;
    private String  tokenPrefix;
    private Integer expirationAfterDays;
    private final String authorizationHeader = HttpHeaders.AUTHORIZATION;
}
