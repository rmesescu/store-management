package com.store.management.exception;

import org.springframework.http.HttpStatus;

public class CartInventoryException extends BaseException{

    public CartInventoryException(String message, HttpStatus status) {
        super(message, status);
    }
}
