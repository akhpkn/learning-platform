package com.example.learningplatform.dto;

import lombok.Data;

@Data
public class LessonDto {
    private Long id;
    private String title;
    private CourseDto course;
}
