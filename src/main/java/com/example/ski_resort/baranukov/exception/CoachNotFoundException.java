package com.example.ski_resort.baranukov.exception;

public class CoachNotFoundException extends RuntimeException{

    public CoachNotFoundException(Long id){
        super("Could not find guest with id " + id);
    }
}
