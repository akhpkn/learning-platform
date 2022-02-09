package com.example.learningplatform.security.jwt;

import com.example.learningplatform.security.userdetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final JwtProperties properties;

    public String generateToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + properties.getExpirationInMs());

        return Jwts.builder()
            .setSubject(userDetails.getId().toString())
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, properties.getSecret())
            .compact();
    }

    public UUID extractUserId(String jwt) {
        String idString = getClaimsFromToken(jwt).getSubject();

        return UUID.fromString(idString);
    }

    public Boolean validateToken(String jwt) {
        try {
            getClaimsFromToken(jwt);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String jwt) {
        return Jwts.parser()
            .setSigningKey(properties.getSecret())
            .parseClaimsJws(jwt)
            .getBody();
    }
}
