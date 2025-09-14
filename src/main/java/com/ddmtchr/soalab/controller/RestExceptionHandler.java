package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.api.ApiErrorResponse;
import com.ddmtchr.soalab.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.NOT_FOUND, LocalDateTime.now(), request, List.of(ex.getMessage()));
    }

    // unhandled ex
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), request, List.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        log.warn(ex.getMessage(), ex);

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();

        return buildResponseEntity(HttpStatus.BAD_REQUEST, LocalDateTime.now(), servletRequest, List.of(ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        log.warn(ex.getMessage(), ex);

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> String.format("Field '%s': %s", err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now(), servletRequest, errors);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        log.warn(ex.getMessage(), ex);

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();

        Object[] args = {ex.getPropertyName(), ex.getValue()};
        String message = "Failed to convert param '" + args[0] + "' with value: '" + args[1] + "'";

        return buildResponseEntity(HttpStatus.BAD_REQUEST, LocalDateTime.now(), servletRequest, List.of(message));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            HttpStatusCode status,
            @NonNull WebRequest request) {

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();

        if (status.is5xxServerError()) {
            log.error(ex.getMessage(), ex);
        } else {
            log.warn(ex.getMessage(), ex);
        }

        ApiErrorResponse error = new ApiErrorResponse(
                (HttpStatus) status,
                LocalDateTime.now(),
                servletRequest.getRequestURI(),
                List.of(ex.getMessage()));

        return super.handleExceptionInternal(ex, error, headers, status, request);
    }


    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, LocalDateTime timestamp, HttpServletRequest request, List<String> messages) {
        ApiErrorResponse response = new ApiErrorResponse(status, timestamp, request.getRequestURI(),  messages);
        return ResponseEntity
                .status(response.getStatus().value())
                .body(response);
    }
}
