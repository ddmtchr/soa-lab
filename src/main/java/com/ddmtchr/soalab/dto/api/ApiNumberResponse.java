package com.ddmtchr.soalab.dto.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "response")
@JacksonXmlRootElement(localName = "response")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiNumberResponse {
    private Number number;
}
