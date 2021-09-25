package com.example.ski_resort.baranukov.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){
        super("Role not found");
    }
}
