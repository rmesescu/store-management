package com.store.management.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleAppBaseException(BaseException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getHttpStatus().value());
        response.put("error", ex.getHttpStatus().getReasonPhrase());
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
}