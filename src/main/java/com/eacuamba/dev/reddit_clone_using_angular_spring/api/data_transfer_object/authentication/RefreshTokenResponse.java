package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RefreshTokenResponse {
    private String token;
}
