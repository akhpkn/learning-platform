package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Employee author;
}
