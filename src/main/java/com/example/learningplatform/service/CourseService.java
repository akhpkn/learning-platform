package com.example.learningplatform.service;

import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.exception.CourseNotFoundException;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EntityMapper mapper;

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

    public void deleteCourse(Long courseId){
        courseRepository.deleteById(courseId);
    }

    public Course editCourse(CourseDto courseDto){
        Course course = courseRepository.findById(courseDto.getId())
                .orElseThrow(CourseNotFoundException::new);
        mapper.updateCourseFromDto(courseDto, course);
        return courseRepository.save(course);
    }
}
