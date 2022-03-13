package com.example.learningplatform.controller;

import com.example.learningplatform.controller.model.LessonCompletionRequest;
import com.example.learningplatform.dto.LessonView;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.service.ProgressService;
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
@RequestMapping("/api/v1/progress")
@AllArgsConstructor
public class ProgressController {

    private final ProgressService service;

    @PostMapping
    public void completeLesson(@RequestBody LessonCompletionRequest request) {
        service.completeLesson(request.getLessonId());
    }

    @GetMapping
    public List<LessonView> getCompletedLessons(@RequestParam("courseId") Long courseId) {
        List<Lesson> lessons = service.getCompletedLessonsByCourseId(courseId);

        return lessons.stream()
            .map(lesson -> new LessonView(lesson.getId(), lesson.getTitle()))
            .collect(Collectors.toList());
    }
}
