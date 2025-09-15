package com.ddmtchr.soalab.dto.team;

import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "team")
@XmlRootElement(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamRequestDto {

    @NotBlank
    private String name;

    @ManyToOne
    private CaveResponseDto cave;
}
