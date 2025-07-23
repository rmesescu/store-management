package com.store.management.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends BaseException {
//    public OutOfStockException(String productName, int requested, int available) {
//        super("Not enough stock for product: " + productName + ". Requested: " + requested + ", Available: " + available);
//    }

    public OutOfStockException(String message, HttpStatus status) {
        super(message, status);
    }
}

