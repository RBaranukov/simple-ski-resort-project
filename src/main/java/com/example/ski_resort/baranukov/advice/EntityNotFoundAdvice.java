package com.example.ski_resort.baranukov.advice;

import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CoachNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String coachNotFoundHandler(CoachNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GuestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String guestNotFoundHandler(GuestNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SkiPassNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String skiPassNotFoundHandler(SkiPassNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequest(Exception ex) {
        return ex.getMessage();
    }
}
