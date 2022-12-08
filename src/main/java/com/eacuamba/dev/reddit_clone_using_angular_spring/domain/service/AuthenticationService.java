package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.RollbackTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.security.jwt.JwtService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.ValidationToken;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.token.Token;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.TokenRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.ValidationTokenRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.token.TokenService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DateTimeHelper dateTimeHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ValidationTokenRepository validationTokenRepository;
    private final MailService mailService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @RollbackTransactional
    public void registerNewUser(User user) {

        user = user.toBuilder()
                .password(this.passwordEncoder.encode(user.getPassword()))
                .enabled(Boolean.FALSE)
                .build();

        this.userRepository.save(user);

        final ValidationToken validationToken = this.generateVerificationToken(user);

        mailService.sendMail(
                MailService.NotificationMail.builder()
                        .to(user.getEmail())
                        .subject("Please activate your account now!")
                        .body(
                                String.format(
                                        """
                                                Thank you for signing up to Reddit Clone Using Spring Boot v3 and Angular v15,
                                                please click on the below url to activate your account :
                                                http://localhost:8080/api/auth/account-verification/%s
                                                """,
                                        validationToken.getToken()
                                )
                        )
                        .build()
        );
    }

    private ValidationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        ValidationToken validationToken = new ValidationToken();
        validationToken.setToken(token);
        validationToken.setUser(user);
        validationToken.setExpirationDate(this.dateTimeHelper.getCurrentInstant());

        this.validationTokenRepository.save(validationToken);
        return validationToken;
    }


    public void verifyAccount(String token) {
        Optional<ValidationToken> validationTokenOptional = this.validationTokenRepository.findByToken(token);
        ValidationToken validationToken = validationTokenOptional.orElseThrow(() -> new RedditCloneException("The token you passed doesn't exists!"));

        this.fetchUserAndEnable(validationToken);
    }

    @RollbackTransactional
    public void fetchUserAndEnable(ValidationToken validationToken) {
        Optional<User> optionalUser = this.userRepository.findByUsername(validationToken.getUser().getUsername());
        User user = optionalUser.orElseThrow(() -> new RedditCloneException("User not found with name " + validationToken.getUser().getUsername()));
        user.setEnabled(Boolean.TRUE);
        this.userRepository.save(user);
    }

    @RollbackTransactional
    public String refreshToken(String refreshToken) {
        Optional<Token> optionalToken = this.tokenRepository.findByValue(refreshToken);
        Token token = optionalToken.orElseThrow(() -> new RedditCloneException("Token not found!"));
        if (Boolean.FALSE.equals(token.getValid())) {
            throw new RedditCloneException("Refresh token is invalid!");
        }
        return this.jwtService.generateJwt(token.getUser());
    }

    @RollbackTransactional
    public void invalidateRefreshToken(String refreshToken) {
        Optional<Token> optionalToken = this.tokenRepository.findByValue(refreshToken);
        Token token = optionalToken.orElseThrow(() -> new RedditCloneException("Token not found!"));

        List<Token> tokenList = this.tokenRepository.findAllByUser(token.getUser());
        tokenList.forEach(t -> {
            t.setValid(Boolean.FALSE);
            this.tokenService.save(t);

        });
    }
}
