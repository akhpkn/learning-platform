package com.example.learningplatform.security;

import com.example.learningplatform.model.Employee;
import com.example.learningplatform.security.exception.WrongPasswordException;
import com.example.learningplatform.security.jwt.JwtUtil;
import com.example.learningplatform.security.model.JwtAuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityFacade {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtUtil.generateToken(authentication);
            return new JwtAuthenticationResponse(jwtToken);
        } catch (BadCredentialsException ex) {
            throw new WrongPasswordException();
        }
    }

    public void encodePassword(Employee employee) {
        String encodedPassword = passwordEncoder.encode(employee.getPassword());

        employee.setPassword(encodedPassword);
    }
}
