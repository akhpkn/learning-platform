package com.example.learningplatform.service;

import com.example.learningplatform.exception.CourseNotFoundException;
import com.example.learningplatform.exception.EmployeeNotFoundException;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.CourseAssignment;
import com.example.learningplatform.model.CourseAssignmentPK;
import com.example.learningplatform.model.Employee;
import com.example.learningplatform.repository.CourseAssignmentRepository;
import com.example.learningplatform.repository.CourseRepository;
import com.example.learningplatform.repository.EmployeeRepository;
import com.example.learningplatform.security.AuthContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseAssignmentService {

    private final CourseRepository courseRepository;
    private final EmployeeRepository employeeRepository;
    private final CourseAssignmentRepository repository;
    private final AuthContext authContext;

    public List<Course> getAssignedCoursesForAuthorizedEmployee() {
        Employee employee = authContext.authorizedEmployee();

        List<CourseAssignment> assignments = repository.findByEmployeeId(employee.getId());

        return assignments.stream()
            .map(CourseAssignment::getCourse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void assignCourse(Long courseId, UUID assigneeId) {
        Employee initiator = authContext.authorizedEmployee();

        Employee assignee = employeeRepository.findById(assigneeId)
            .orElseThrow(EmployeeNotFoundException::new);
        Course course = courseRepository.findById(courseId)
            .orElseThrow(CourseNotFoundException::new);

        CourseAssignment assignment = new CourseAssignment();
        assignment.setId(new CourseAssignmentPK(assigneeId, courseId));
        assignment.setCourse(course);
        assignment.setEmployee(assignee);
        assignment.setInitiator(initiator);

        repository.save(assignment);
    }
}
