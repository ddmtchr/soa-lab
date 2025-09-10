package com.ddmtchr.soalab.dto.dragon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JacksonXmlRootElement(localName = "types-count")
@XmlRootElement(name = "types-count")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountListDto {

    @JacksonXmlProperty(localName = "entry")
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "entry")
    private List<DragonTypeCountDto> typeCounts;
}
