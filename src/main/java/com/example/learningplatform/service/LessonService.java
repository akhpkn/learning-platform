package com.example.learningplatform.service;

import com.example.learningplatform.exception.CourseNotFoundException;
import com.example.learningplatform.exception.MissingRightsException;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Employee;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.repository.CourseRepository;
import com.example.learningplatform.repository.LessonRepository;
import com.example.learningplatform.security.AuthContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AuthContext authContext;

    public List<Lesson> getAllByCourseId(Long courseId) {
        return lessonRepository.findAllByCourseId(courseId);
    }

    @Transactional
    public Lesson createLesson(String title, Long courseId) {
        Employee employee = authContext.authorizedEmployee();
        Course course = courseRepository.findById(courseId)
            .orElseThrow(CourseNotFoundException::new);

        if (course.getAuthor() != employee) {
            throw new MissingRightsException();
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    public Lesson saveLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    public Lesson getLessonById(Long lessonId){
        return lessonRepository.getById(lessonId);
    }
}
