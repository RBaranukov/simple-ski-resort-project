package com.example.ski_resort.baranukov.exception;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(Long id) {
        super("Could not find guest with id " + id);
    }
}
