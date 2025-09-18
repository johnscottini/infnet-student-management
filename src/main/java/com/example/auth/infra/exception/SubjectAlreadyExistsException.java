package com.example.auth.infra.exception;

public class SubjectAlreadyExistsException extends RuntimeException {
    public SubjectAlreadyExistsException(String message) {
        super(message);
    }
}
