package com.example.ski_resort.baranukov.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
}
