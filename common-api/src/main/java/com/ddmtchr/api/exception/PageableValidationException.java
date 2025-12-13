package com.ddmtchr.api.exception;

import java.io.Serializable;

public class PageableValidationException extends RuntimeException implements Serializable {
    public PageableValidationException(String message) {
        super(message);
    }
}
