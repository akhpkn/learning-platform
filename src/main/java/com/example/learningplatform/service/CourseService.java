package com.example.learningplatform.service;

import com.example.learningplatform.exception.CourseNotFoundException;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(CourseNotFoundException::new);
    }

    public Course createCourse(String title, String description) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);

        return courseRepository.save(course);
    }
}
