package com.ddmtchr.soalab.dto.dragon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.*;
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
    @JacksonXmlElementWrapper(localName = "content")
    @XmlElement(name = "dragon")
    @XmlElementWrapper(name = "content")
    private List<DragonResponseDto> content;

    private int page;
    private int size;
    private long total;
}
