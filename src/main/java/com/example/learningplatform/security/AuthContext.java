package com.example.learningplatform.security;

import com.example.learningplatform.exception.EmployeeNotFoundException;
import com.example.learningplatform.model.Employee;
import com.example.learningplatform.repository.EmployeeRepository;
import com.example.learningplatform.security.userdetails.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthContext {

    private final EmployeeRepository repository;

    public Employee authorizedEmployee() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return repository.findById(userDetails.getId())
            .orElseThrow(EmployeeNotFoundException::new);
    }
}
