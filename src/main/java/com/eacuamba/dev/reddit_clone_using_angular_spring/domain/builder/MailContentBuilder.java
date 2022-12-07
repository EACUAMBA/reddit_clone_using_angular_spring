package com.eacuamba.dev.reddit_clone_using_angular_spring.domain.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    public String build(String message, String title){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("title", title);
        return this.templateEngine.process("mail/verification-token-mail-template.html", context);
    }
}
