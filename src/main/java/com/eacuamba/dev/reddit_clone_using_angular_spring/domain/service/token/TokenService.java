package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.token;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.TokenType;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @RollbackTransactional
    public Token save(Token token){
        return this.tokenRepository.save(token);
    }

    public Token generateRefreshToken(User user){
        String uuid = UUID.randomUUID().toString();
        Token token = Token.builder()
                .user(user)
                .tokenType(TokenType.REFRESH_JWT)
                .value(uuid)
                .build();
        this.save(token);

        return token;
    }
}
