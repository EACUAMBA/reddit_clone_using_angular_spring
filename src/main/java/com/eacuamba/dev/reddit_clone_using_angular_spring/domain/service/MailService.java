package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.builder.MailContentBuilder;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.exception.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(final NotificationMail notificationMail){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("reddit_clone_using_angular_spring@email.com");
            mimeMessageHelper.setTo(notificationMail.getTo());
            mimeMessageHelper.setSubject(notificationMail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationMail.getBody(), "Verification Token!"));
        };

        try{
            this.javaMailSender.send(mimeMessagePreparator);
            log.info("Email '{}' sent to {}!", notificationMail.subject, notificationMail.to);
        }catch (MailException mailException){
            throw new RedditCloneException("Exception occurred trying to send email. Description: " + mailException.getMessage());
        }
    }

    @Async
    public void sendCommentNotificationEmail(String message, User user) {
        NotificationMail notificationMail = NotificationMail.builder()
                .subject(user.getUsername() + " commented on your post!")
                .body(message)
                .to(user.getEmail())
                .build();
        this.sendMail(notificationMail);
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotificationMail{
        private String to;
        private String subject;
        private String body;
    }
}

