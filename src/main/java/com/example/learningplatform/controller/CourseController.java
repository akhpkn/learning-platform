package com.example.learningplatform.controller;

import com.example.learningplatform.controller.model.CourseCreationRequest;
import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.dto.CourseView;
import com.example.learningplatform.dto.LessonView;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.service.CourseService;
import com.example.learningplatform.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final EntityMapper mapper;

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/created-by-employee")
    public List<CourseDto> getCoursesCreatedByEmployee() {
        return courseService.getCoursesCreatedByAuthorizedEmployee().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/{courseId}")
    public CourseDto getById(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return mapper.toDto(course);
    }

    @GetMapping("/{courseId}/view")
    public CourseView getCourseView(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        List<Lesson> lessons = lessonService.getAllByCourseId(course.getId());

        CourseView view = new CourseView();

        view.setId(course.getId());
        view.setTitle(course.getTitle());
        view.setDescription(course.getDescription());
        view.setLessons(
            lessons.stream()
                .map(lesson -> new LessonView(lesson.getId(), lesson.getTitle()))
                .collect(Collectors.toList())
        );

        return view;
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseCreationRequest request) {
        Course course = courseService.createCourse(request.getTitle(), request.getDescription());
        return mapper.toDto(course);
    }
}
