package com.ddmtchr.soalab.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomResponseStatusException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
