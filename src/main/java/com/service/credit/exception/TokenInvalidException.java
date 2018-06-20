package com.service.credit.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token invalid");
    }

}