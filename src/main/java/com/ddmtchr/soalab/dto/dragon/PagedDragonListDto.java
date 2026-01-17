package com.ddmtchr.soalab.dto.dragon;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "dragonsPage")
@XmlRootElement(name = "dragonsPage")
@XmlType(name = "DragonsPage")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagedDragonListDto {

//    @JacksonXmlProperty(localName = "dragon")
//    @JacksonXmlElementWrapper(localName = "content")
    @XmlElement(name = "dragon")
    @XmlElementWrapper(name = "content")
    private List<DragonResponseDto> content;

    @XmlElement
    private int page;

    @XmlElement
    private int size;

    @XmlElement
    private long total;
}
