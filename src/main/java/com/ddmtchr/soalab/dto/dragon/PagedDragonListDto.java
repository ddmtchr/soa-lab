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
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JacksonXmlRootElement(localName = "dragonsPage")
@XmlRootElement(name = "dragonsPage")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagedDragonListDto {

    @JacksonXmlProperty(localName = "dragon")
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "dragon")
    private List<DragonResponseDto> dragons;

    private int page;
    private int size;
    private long total;
}
