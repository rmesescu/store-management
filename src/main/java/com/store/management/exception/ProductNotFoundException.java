package com.store.management.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
//    public NoProductFoundException(String productName, int requested, int available) {
//        super("Not enough stock for product: " + productName + ". Requested: " + requested + ", Available: " + available, HT);
//    }

    public ProductNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}