package com.example.learningplatform.service;

import com.example.learningplatform.model.Employee;
import com.example.learningplatform.model.Role;
import com.example.learningplatform.repository.EmployeeRepository;
import com.example.learningplatform.repository.RoleRepository;
import com.example.learningplatform.exception.EmployeeAlreadyExistsException;
import com.example.learningplatform.security.SecurityFacade;
import com.example.learningplatform.security.model.JwtAuthenticationResponse;
import com.example.learningplatform.security.model.SignInRequest;
import com.example.learningplatform.security.model.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@AllArgsConstructor
public class AuthService {

    private final SecurityFacade securityFacade;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }

        Role basicRole = roleRepository.findByName(Role.RoleName.ROLE_EMPLOYEE);

        Employee employee = Employee.builder()
            .email(request.getEmail())
            .name(request.getName())
            .surname(request.getSurname())
            .password(request.getPassword())
            .roles(Set.of(basicRole))
            .build();

        securityFacade.encodePassword(employee);

        employeeRepository.save(employee);

        return securityFacade.authenticate(request.getEmail(), request.getPassword());
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        return securityFacade.authenticate(request.getEmail(), request.getPassword());
    }
}
