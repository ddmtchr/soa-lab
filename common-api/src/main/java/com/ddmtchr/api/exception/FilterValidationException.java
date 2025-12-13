package com.ddmtchr.api.exception;

import java.io.Serializable;

public class FilterValidationException extends RuntimeException implements Serializable {
    public FilterValidationException(String message) {
        super(message);
    }

    public FilterValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
