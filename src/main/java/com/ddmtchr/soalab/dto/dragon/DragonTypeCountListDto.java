package com.ddmtchr.soalab.dto.dragon;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "types-count")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Types-count")
@XmlRootElement(name = "types-count")
@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountListDto {

//    @JacksonXmlProperty(localName = "entry")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "entry", required = true)
    private List<DragonTypeCountDto> typeCounts;
}
