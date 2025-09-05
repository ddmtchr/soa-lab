package com.ddmtchr.soalab.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@XmlRootElement(name = "error")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiErrorResponse {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
}
