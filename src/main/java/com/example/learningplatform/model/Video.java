package com.example.learningplatform.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "videos")
@NoArgsConstructor
public class Video extends File {

    public Video(String url, String type) {
        super(url, type);
    }
}
