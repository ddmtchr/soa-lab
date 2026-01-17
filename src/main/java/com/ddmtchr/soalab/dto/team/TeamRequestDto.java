package com.ddmtchr.soalab.dto.team;

import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "team")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Team")
@XmlRootElement(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamRequestDto {

    @NotBlank
    @XmlElement(required = true)
    private String name;

    @Valid
    @XmlElement
    private CaveResponseDto cave;
}
