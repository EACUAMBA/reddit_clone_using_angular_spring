package com.eacuamba.dev.reddit_clone_using_angular_spring.api.controller;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.RegisterRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){

        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build();

        this.authenticationService.registerNewUser(user);

        return new ResponseEntity<>("User registration successful!", HttpStatus.OK);
    }

    @GetMapping("account-verification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable("token") String token){
        this.authenticationService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully!", HttpStatus.OK);
    }
}
