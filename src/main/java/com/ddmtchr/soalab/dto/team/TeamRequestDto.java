package com.ddmtchr.soalab.dto.team;

import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "team")
@XmlRootElement(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamRequestDto {

    @NotBlank
    private String name;

    @Valid
    private CaveResponseDto cave;
}
