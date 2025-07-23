package com.store.management.exception;

import org.springframework.http.HttpStatus;

public class BadOrderException extends BaseException{

    public BadOrderException(String message, HttpStatus status) {
        super(message, status);
    }
}
