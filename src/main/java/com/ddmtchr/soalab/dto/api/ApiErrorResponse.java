package com.ddmtchr.soalab.dto.api;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "error")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Error")
@XmlRootElement(name = "error")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiErrorResponse {

    @XmlElement
    private HttpStatus status;

    @XmlElement
    private LocalDateTime timestamp;

    @XmlElement
    private String path;

//    @JacksonXmlProperty(localName = "message")
//    @JacksonXmlElementWrapper(localName = "messages")
    @XmlElement(name = "message")
    @XmlElementWrapper(name = "messages")
    private List<String> messages;
}
