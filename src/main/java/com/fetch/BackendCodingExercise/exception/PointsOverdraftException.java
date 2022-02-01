package com.fetch.BackendCodingExercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Points balance can not be negative")
public class PointsOverdraftException extends Exception {

    public PointsOverdraftException() {
        super("Transactions that create a negative point balance are not permitted");
    }
}
