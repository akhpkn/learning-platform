package com.example.learningplatform.security.userdetails;

import com.example.learningplatform.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailWithRoles(username)
            .map(CustomUserDetails::fromEmployee)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Пользователь не найден");
            });
    }

    public UserDetails loadUserById(UUID id) {
        return repository.findByIdWithRoles(id)
            .map(CustomUserDetails::fromEmployee)
            .orElseThrow(() -> {
               throw new UsernameNotFoundException("Пользователь не найден");
            });
    }
}
