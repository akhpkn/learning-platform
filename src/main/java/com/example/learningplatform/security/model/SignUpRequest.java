package com.example.learningplatform.security.model;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String name;
    private String surname;
    private String password;
}
