package com.ddmtchr.soalab.dto.api;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response")
@XmlRootElement(name = "response")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiNumberResponse {

    @XmlElement
    private Number number;
}
