package com.ddmtchr.soalab.exception;

import org.springframework.http.HttpStatus;

public class FilterValidationException extends CustomResponseStatusException {
    public FilterValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
