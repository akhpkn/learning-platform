package com.example.learningplatform.controller;

import com.example.learningplatform.controller.model.CourseAssignmentRequest;
import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.service.CourseAssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/course-assignment")
@AllArgsConstructor
public class CourseAssignmentController {

    private final CourseAssignmentService service;
    private final EntityMapper mapper;

    @GetMapping
    public List<CourseDto> getAssignedCourses() {
        return service.getAssignedCoursesForAuthorizedEmployee().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public void assignCourse(@RequestBody CourseAssignmentRequest request) {
        service.assignCourse(request.getCourseId(), request.getEmployeeId());
    }
}
