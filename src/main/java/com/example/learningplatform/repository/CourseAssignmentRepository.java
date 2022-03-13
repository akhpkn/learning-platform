package com.example.learningplatform.repository;

import com.example.learningplatform.model.CourseAssignment;
import com.example.learningplatform.model.CourseAssignmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, CourseAssignmentPK> {

    @Query("select ca from CourseAssignment ca join fetch ca.course where ca.employee.id=:employeeId")
    List<CourseAssignment> findByEmployeeId(@Param("employeeId") UUID employeeId);
}
