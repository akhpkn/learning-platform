package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "lesson_progress")
@Data
public class LessonProgress {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private EmployeeProgress progress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;
}
