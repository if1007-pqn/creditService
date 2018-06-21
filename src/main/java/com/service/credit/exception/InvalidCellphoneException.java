package com.service.credit.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class InvalidCellphoneException extends RuntimeException {
    public InvalidCellphoneException() {
        super("This cellphone doesn't seem right");
    }

}