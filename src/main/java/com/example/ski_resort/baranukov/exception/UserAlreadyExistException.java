package com.example.ski_resort.baranukov.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(){
        super("User already exists");
    }
}