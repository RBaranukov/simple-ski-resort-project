package com.example.ski_resort.baranukov.exception;

public class UserOrPasswordIncorrectException extends RuntimeException{
    public UserOrPasswordIncorrectException(){
        super("User or password incorrect");
    }
}
