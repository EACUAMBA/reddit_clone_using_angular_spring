package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions;

import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.I18NMessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ExceptionBuilder {
    private final I18NMessageHelper i18NMessageHelper;

    public <T> EntityNotFoundException buildEntityNotFoundBy(String entity, String property, T value) {
        String message = this.i18NMessageHelper
                .getMessage(
                        "entity_x_with_y_with_value_z_was_not_found",
                        property,
                        entity,
                        Objects.toString(value)
                );

        return new EntityNotFoundException(message);
    }

    public <T> EntityNotFoundException buildEntityNotFoundById(String entity, T id){
        String message = this.i18NMessageHelper
                .getMessage(
                        "x_with_id_y_was_not_found",
                        entity,
                        Objects.toString(id)
                );
        return new EntityNotFoundException(message);
    }

    public NoAuthenticatedUserException buildNoAuthenticatedUser(){
        String message = this.i18NMessageHelper.getMessage("there_is_no_authenticated_user_to_perform_the_action");
        return new NoAuthenticatedUserException(message);
    }
}
