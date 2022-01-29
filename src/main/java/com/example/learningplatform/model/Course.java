package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
@Data
public class Course {
    @Id
    @GeneratedValue(generator = "courses_id_seq_gen")
    @SequenceGenerator(
        name = "courses_id_seq_gen",
        sequenceName = "courses_id_seq",
        allocationSize = 1
    )
    private Long id;

    private String title;

    private String description;
}
