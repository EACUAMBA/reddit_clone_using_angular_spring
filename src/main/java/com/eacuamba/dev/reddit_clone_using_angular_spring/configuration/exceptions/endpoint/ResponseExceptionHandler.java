package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.endpoint;

import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.EntityNotFoundException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.RedditCloneException;
import com.eacuamba.dev.reddit_clone_using_angular_spring.helper.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

;

@RequiredArgsConstructor
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private final DateTimeHelper dateTimeHelper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<ExceptionBody.Field> fields = new ArrayList<>();

        for(ObjectError objectError : ex.getBindingResult().getAllErrors()){
            FieldError fieldError = (FieldError) objectError;
            fields.add(
                    new ExceptionBody.Field(
                            fieldError.getField(),
                            this.messageSource.getMessage(objectError, Locale.getDefault())
                    )
            );
        }

        ExceptionBody exceptionBody = ExceptionBody.builder()
                .dateTime(this.dateTimeHelper.getCurrentLocalDateTime())
                .title(this.messageSource.getMessage("occurred_an_issue_validating_the_fields_in_your_request", new Object[]{}, "occurred_an_issue", Locale.getDefault()))
                .status(status.value())
                .fields(fields)
                .build();

        return this.handleExceptionInternal(ex, exceptionBody, headers, status, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RedditCloneException.class)
    public ResponseEntity<Object> handleException(RedditCloneException redditCloneException, WebRequest webRequest){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionBody exceptionBody = ExceptionBody.builder()
                .status(httpStatus.value())
                .dateTime(this.dateTimeHelper.getCurrentLocalDateTime())
                .title(redditCloneException.getMessage())
                .build();
        return handleExceptionInternal(redditCloneException, exceptionBody, new HttpHeaders(), httpStatus, webRequest);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException, WebRequest webRequest){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionBody exceptionBody = ExceptionBody.builder()
                .status(httpStatus.value())
                .dateTime(this.dateTimeHelper.getCurrentLocalDateTime())
                .title(entityNotFoundException.getMessage())
                .build();
        return handleExceptionInternal(entityNotFoundException, exceptionBody, new HttpHeaders(), httpStatus, webRequest);
    }
}
