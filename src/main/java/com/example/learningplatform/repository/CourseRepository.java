package com.example.learningplatform.repository;

import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByAuthor(Employee employee);
}
