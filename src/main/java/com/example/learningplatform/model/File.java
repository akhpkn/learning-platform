package com.example.learningplatform.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(generator = "files_id_seq_gen")
    @SequenceGenerator(
            name = "files_id_seq_gen",
            sequenceName = "files_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String url;

    private String type;

    public File(String url, String type) {
        this.url = url;
        this.type = type;
    }
}