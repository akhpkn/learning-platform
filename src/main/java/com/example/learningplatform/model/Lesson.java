package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lessons")
@Data
public class Lesson {

    @Id
    @GeneratedValue(generator = "lessons_id_seq_gen")
    @SequenceGenerator(
        name = "lessons_id_seq_gen",
        sequenceName = "lessons_id_seq",
        allocationSize = 1
    )
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private Course course;
}
