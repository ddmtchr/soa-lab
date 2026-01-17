package com.ddmtchr.soalab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {
    private static final QName STATUS = new QName("status");
    private static final QName CODE = new QName("code");
    private static final QName MESSAGE = new QName("message");
    private static final QName TIMESTAMP = new QName("timestamp");

    @Override
    protected void customizeFault(Object endpoint, Exception exception, SoapFault fault) {
        final List<String> messages = getMessage(exception);
        final SoapFaultDetail detail = fault.addFaultDetail();
        System.out.println("Caught exception: " + exception);
        if (exception instanceof ResponseStatusException responseStatusException) {
            final int code = responseStatusException.getStatusCode().value();
            detail.addFaultDetailElement(CODE).addText(String.valueOf(code));
            detail.addFaultDetailElement(STATUS).addText(HttpStatus.valueOf(code).name());
        } else {
            detail.addFaultDetailElement(CODE).addText("500");
            detail.addFaultDetailElement(STATUS).addText(HttpStatus.valueOf("500").name());
        }
        detail.addFaultDetailElement(TIMESTAMP).addText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")));

        for (String msg : messages) {
            detail.addFaultDetailElement(MESSAGE).addText(msg);
        }
    }

    private List<String> getMessage(Exception exception) {
        if (exception instanceof HttpMessageNotReadableException) {
            return List.of("Invalid request");
        }
        if (exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return List.of("Bad request. Invalid param: %s".formatted(methodArgumentTypeMismatchException.getPropertyName()));
        }

        if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            return getValidationMessage(methodArgumentNotValidException);
        }
        return List.of(exception.getMessage());
    }

    private List<String> getValidationMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Invalid request body. Field '%s' %s",
                        error.getField(), error.getDefaultMessage()))
                .toList();
    }
}
