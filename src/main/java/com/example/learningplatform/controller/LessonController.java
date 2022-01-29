package com.example.learningplatform.controller;

import com.example.learningplatform.controller.model.LessonCreationRequest;
import com.example.learningplatform.dto.LessonDto;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lessons")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final EntityMapper mapper;

    @GetMapping
    public List<LessonDto> getAllByCourseId(@RequestParam("courseId") Long courseId) {
        return lessonService.getAllByCourseId(courseId).stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public LessonDto createLesson(@RequestBody LessonCreationRequest request) {
        Lesson lesson = lessonService.createLesson(request.getTitle(), request.getCourseId());
        return mapper.toDto(lesson);
    }
}
