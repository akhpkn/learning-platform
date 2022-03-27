package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "scores")
@Data
public class Score {

    @Id
    @GeneratedValue(generator = "scores_id_seq_gen")
    @SequenceGenerator(
            name = "scores_id_seq_gen",
            sequenceName = "scores_id_seq",
            allocationSize = 1
    )
    private Long id;

    private UUID authorId;

    private Long courseId;

    private Long score;
}