package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.CustomTransactional;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.ValidationToken;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.UserRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.repository.ValidationTokenRepository;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DateTimeHelper dateTimeHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ValidationTokenRepository validationTokenRepository;
    private final MailService mailService;

    @CustomTransactional
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
                        .subject("Please activete your account now!")
                        .body(
                                String.format(
                                        """
                                        Thank you for signing up to Spring Reddit,
                                        please click on the below url to activate your account :
                                        http://localhost:8080/api/auth/accountVerification/%s
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


}
