package com.example.learningplatform.controller.model;

import lombok.Data;

@Data
public class LessonCreationRequest {
    private String title;
    private Long courseId;
}
