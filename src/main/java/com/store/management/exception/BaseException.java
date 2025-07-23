package com.store.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}