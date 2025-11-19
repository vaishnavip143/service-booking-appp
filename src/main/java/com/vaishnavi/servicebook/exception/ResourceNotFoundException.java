package com.vaishnavi.servicebook.exception;
public class resourcenotfoundexception extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
