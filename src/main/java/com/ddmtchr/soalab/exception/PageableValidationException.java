package com.ddmtchr.soalab.exception;

import org.springframework.http.HttpStatus;

public class PageableValidationException extends CustomResponseStatusException {
    public PageableValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
