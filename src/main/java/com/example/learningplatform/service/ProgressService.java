package com.example.learningplatform.service;

import com.example.learningplatform.exception.LessonNotFoundException;
import com.example.learningplatform.model.Employee;
import com.example.learningplatform.model.EmployeeProgress;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.model.LessonProgress;
import com.example.learningplatform.repository.EmployeeProgressRepository;
import com.example.learningplatform.repository.LessonProgressRepository;
import com.example.learningplatform.repository.LessonRepository;
import com.example.learningplatform.security.AuthContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProgressService {

    private final EmployeeProgressRepository employeeProgressRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final AuthContext authContext;

    public List<Lesson> getCompletedLessonsByCourseId(Long courseId) {
        Employee employee = authContext.authorizedEmployee();

        return getCompletedLessonsByEmployeeAndCourseId(employee, courseId);
    }

    private List<Lesson> getCompletedLessonsByEmployeeAndCourseId(Employee employee, Long courseId) {
        return lessonProgressRepository
            .findByEmployeeIdAndCourseId(employee.getId(), courseId)
            .stream()
            .map(LessonProgress::getLesson)
            .collect(Collectors.toList());
    }

    @Transactional
    public void completeLesson(Long lessonId) {
        Employee employee = authContext.authorizedEmployee();

        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(LessonNotFoundException::new);

        EmployeeProgress progress = getOrCreateProgress(employee);

        List<Lesson> courseCompletedLessons =
            getCompletedLessonsByEmployeeAndCourseId(employee, lesson.getCourse().getId());
        List<Lesson> courseLessons = lessonRepository.findAllByCourseId(lesson.getCourse().getId());

        boolean lessonIsCompleted = courseCompletedLessons.contains(lesson);

        if (!lessonIsCompleted) {
            if (courseLessons.size() == courseCompletedLessons.size() + 1) {
                progress.setCoursesCompleted(progress.getCoursesCompleted() + 1);
            }

            LessonProgress lessonProgress = new LessonProgress();
            lessonProgress.setProgress(progress);
            lessonProgress.setLesson(lesson);

            lessonProgressRepository.save(lessonProgress);
        }
    }

    private EmployeeProgress getOrCreateProgress(Employee employee) {
        return employeeProgressRepository.findByEmployee(employee)
            .orElseGet(() -> {
                EmployeeProgress newEmployeeProgress = new EmployeeProgress();
                newEmployeeProgress.setEmployee(employee);
                employeeProgressRepository.save(newEmployeeProgress);
                return newEmployeeProgress;
            });
    }
}
