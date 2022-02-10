package com.example.learningplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MissingRightsException extends RuntimeException {

    public MissingRightsException() {
        super("У вас недостаточно прав");
    }
}
