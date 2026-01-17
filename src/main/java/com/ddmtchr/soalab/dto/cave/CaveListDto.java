package com.ddmtchr.soalab.dto.cave;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "caves")
@XmlRootElement(name = "caves")
@XmlType(name = "Caves")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CaveListDto {

//    @JacksonXmlProperty(localName = "cave")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "cave", required = true)
    private List<CaveResponseDto> content;
}
