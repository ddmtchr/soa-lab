package com.ddmtchr.soalab.dto.cave;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "cave")
@XmlRootElement(name = "cave")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaveRequestDto {

    @NotBlank
    private String name;
}
