package com.example.auth.controller;

import com.example.auth.exception.ExistingUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExistingUserException.class)
    public String handleExistingUserException(ExistingUserException e) {
        log.debug(e.getMessage());
        return e.getMessage();
    }
}
