package com.ddmtchr.soalab.dto.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "error")
@JacksonXmlRootElement(localName = "error")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiErrorResponse {

    private HttpStatus status;

    private LocalDateTime timestamp;

    private String path;

    @JacksonXmlProperty(localName = "message")
    @JacksonXmlElementWrapper(localName = "messages")
    @XmlElement(name = "message")
    @XmlElementWrapper(name = "messages")
    private List<String> messages;
}
