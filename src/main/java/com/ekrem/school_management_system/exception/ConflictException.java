package com.ekrem.school_management_system.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
