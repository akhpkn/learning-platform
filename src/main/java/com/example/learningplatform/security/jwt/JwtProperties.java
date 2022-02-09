package com.example.learningplatform.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@Configuration
@Data
public class JwtProperties {
    private String secret;
    private Long expirationInMs;
}
