package com.example.auth.exception;

public class ExistingUserException extends IllegalArgumentException {
    public ExistingUserException(String email) {
        super(email + " already exists");
    }
}
