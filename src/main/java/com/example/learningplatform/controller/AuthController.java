package com.example.learningplatform.controller;

import com.example.learningplatform.service.AuthService;
import com.example.learningplatform.security.model.JwtAuthenticationResponse;
import com.example.learningplatform.security.model.SignInRequest;
import com.example.learningplatform.security.model.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
