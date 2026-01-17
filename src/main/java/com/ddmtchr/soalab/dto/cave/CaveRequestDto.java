package com.ddmtchr.soalab.dto.cave;

import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "cave")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cave")
@XmlRootElement(name = "cave")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaveRequestDto {

    @NotBlank
    @XmlElement(required = true)
    private String name;
}
