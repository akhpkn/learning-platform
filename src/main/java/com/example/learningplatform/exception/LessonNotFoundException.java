package com.example.learningplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException() {
        super("Lesson not found");
    }
}