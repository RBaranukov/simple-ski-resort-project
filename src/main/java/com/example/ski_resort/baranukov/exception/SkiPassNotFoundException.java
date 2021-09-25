package com.example.ski_resort.baranukov.exception;

public class SkiPassNotFoundException extends RuntimeException{

    public SkiPassNotFoundException(Long id){
        super("Could not find SkiPass with id " + id);
    }
}
