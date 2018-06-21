package com.service.credit.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class InsufficientCreditsException extends RuntimeException {
    public InsufficientCreditsException() {
        super("Don't have the credit requested");
    }

}