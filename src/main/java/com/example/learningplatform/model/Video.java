package com.example.learningplatform.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "videos")
public class Video extends File {

    public Video(String url, String type) {
        super(url, type);
    }
}
