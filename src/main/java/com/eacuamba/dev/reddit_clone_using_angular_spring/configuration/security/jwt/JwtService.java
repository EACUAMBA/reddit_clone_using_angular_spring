package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.TokenType;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.token.TokenService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user.UserService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.DateTimeHelper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserService userService;
    private final DateTimeHelper dateTimeHelper;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;
    private final TokenService tokenService;

    public String generateJwt(User user){
        user = this.userService.loadUserByUsername(user.getUsername());
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(this.dateTimeHelper.getCurrenteDate())
                .setExpiration(this.dateTimeHelper.convertLocalDateToDate(
                                this.dateTimeHelper.getCurrentLocalDate().plusDays(
                                        this.jwtConfiguration.getExpirationAfterDays()
                                )
                        )
                )
                .signWith(this.secretKey)
                .compact();

        Token token = Token.builder()
                .tokenType(TokenType.JWT)
                .user(user)
                .value(jwt)
                .build();
        this.tokenService.save(token);

        return jwt;
    }
}
