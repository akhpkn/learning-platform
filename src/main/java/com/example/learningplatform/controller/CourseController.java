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
import com.example.learningplatform.service.RecommenderService;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final RecommenderService recommenderService;
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

    @GetMapping("/user/{userId}")
    public List<CourseDto> getByUser(@PathVariable Long userId) {
        return courseService.getCoursesByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
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

    @DeleteMapping("/{courseId}")
    public void deleteById(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/{courseId}")
    public CourseDto editCourse(@RequestBody CourseDto request) {
        Course course = courseService.editCourse(request);
        return mapper.toDto(course);
    }

    @GetMapping("/recs")
    public List<RecommendedItem> getRecommendations(@RequestParam String personUUID, @RequestParam Integer numOfRecs) throws TasteException {
        recommenderService.getPreferencesNew();
        return recommenderService.recommendNew(UUID.fromString(personUUID),numOfRecs);
    }
}
