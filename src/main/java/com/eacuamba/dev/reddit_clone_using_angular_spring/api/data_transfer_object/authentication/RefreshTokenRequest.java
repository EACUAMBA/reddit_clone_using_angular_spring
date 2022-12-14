package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
