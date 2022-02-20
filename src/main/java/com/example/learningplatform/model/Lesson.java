package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private File video;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
}
