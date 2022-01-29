package com.example.learningplatform.repository;

import com.example.learningplatform.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson l join fetch l.course c where c.id=:courseId")
    List<Lesson> findAllByCourseId(@Param("courseId") Long courseId);
}
