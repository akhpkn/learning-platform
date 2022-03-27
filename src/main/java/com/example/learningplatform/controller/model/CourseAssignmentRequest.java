package com.example.learningplatform.controller.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseAssignmentRequest {
    private UUID employeeId;
    private Long courseId;
}
