package com.clockworks.clock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateRecordFoundException extends RuntimeException {

    public DuplicateRecordFoundException(String message) {
        super(message);
    }
}
