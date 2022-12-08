package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.exceptions.endpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
public class ExceptionBody {
    @Builder.Default
    private Integer status = HttpStatus.BAD_REQUEST.value();
    private LocalDateTime dateTime;
    private String title;
    @Builder.Default
    private List<Field> fields = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    public static class Field{
        private String name;
        private String message;
    }
}
