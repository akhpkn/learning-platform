package com.example.learningplatform.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeRateDto {
    private UUID employeeId;
    private String name;
    private String surname;
    private String email;
    private Long coursesCompleted;
}
