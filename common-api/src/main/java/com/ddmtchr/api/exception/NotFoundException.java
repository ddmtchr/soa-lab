package com.ddmtchr.api.exception;

import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {
    public NotFoundException(String message) {
        super(message);
    }
}
