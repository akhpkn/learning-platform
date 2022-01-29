package com.example.learningplatform.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseView {
    private Long id;
    private String title;
    private String description;

    private List<LessonView> lessons;
}
