package com.service.credit.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)

public class ServiceNotReachableException extends RuntimeException {
    public ServiceNotReachableException(String service) {
        super(service + " service unavailable");
    }

}
