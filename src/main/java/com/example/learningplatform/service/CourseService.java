package com.example.learningplatform.service;

import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.exception.CourseNotFoundException;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Employee;
import com.example.learningplatform.repository.CourseRepository;
import com.example.learningplatform.security.AuthContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EntityMapper mapper;
    private final AuthContext authContext;


    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesCreatedByAuthorizedEmployee() {
        Employee employee = authContext.authorizedEmployee();
        return courseRepository.findByAuthor(employee);
    }

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(CourseNotFoundException::new);
    }

    @Transactional
    public Course createCourse(String title, String description) {
        Employee author = authContext.authorizedEmployee();

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setAuthor(author);

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
