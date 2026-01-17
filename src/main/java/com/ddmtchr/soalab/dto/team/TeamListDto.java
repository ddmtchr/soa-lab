package com.ddmtchr.soalab.dto.team;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "teams")
@XmlRootElement(name = "teams")
@XmlType(name = "Teams")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamListDto {

//    @JacksonXmlProperty(localName = "team")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "team", required = true)
    private List<TeamResponseDto> content;
}
