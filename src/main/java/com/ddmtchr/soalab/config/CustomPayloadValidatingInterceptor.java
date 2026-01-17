package com.ddmtchr.soalab.config;

import org.springframework.http.HttpStatus;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.xml.sax.SAXParseException;

import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomPayloadValidatingInterceptor extends PayloadValidatingInterceptor {
    private static final QName STATUS = new QName("status");
    private static final QName CODE = new QName("code");
    private static final QName MESSAGE = new QName("message");
    private static final QName TIMESTAMP = new QName("timestamp");

    @Override
    protected boolean handleRequestValidationErrors(MessageContext messageContext, SAXParseException[] errors) {
        SoapMessage response = (SoapMessage) messageContext.getResponse();
        SoapBody body = response.getSoapBody();

        SoapFault fault = body.addServerOrReceiverFault("Validation Error", null);
        fault.addFaultDetail();
        SoapFaultDetail detail = fault.getFaultDetail();
        detail.addFaultDetailElement(CODE).addText(errors[0].getMessage().contains("cvc") ?
                String.valueOf(422) :
                String.valueOf(400));
        detail.addFaultDetailElement(STATUS).addText(errors[0].getMessage().contains("cvc") ?
                HttpStatus.valueOf(422).name() :
                HttpStatus.valueOf(400).name());
        detail.addFaultDetailElement(TIMESTAMP).addText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")));
        detail.addFaultDetailElement(MESSAGE).addText(errors[0].getMessage());
        return false;
    }
}
