package com.example.learningplatform.repository;

import com.example.learningplatform.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {

    @Query(
        "select lp from LessonProgress lp " +
            "join fetch lp.progress p " +
            "join fetch lp.lesson l " +
            "where p.employee.id=:employeeId and l.course.id=:courseId"
    )
    List<LessonProgress> findByEmployeeIdAndCourseId(
        @Param("employeeId") UUID employeeId,
        @Param("courseId") Long courseId
    );
}
