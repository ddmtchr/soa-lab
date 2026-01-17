package com.ddmtchr.soalab.dto.api.filter;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "filters")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Filters")
@XmlRootElement(name = "filters")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterRequestDto {

//    @JacksonXmlProperty(localName = "filter")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "filter")
    private List<@Valid FilterCriteria> filters;
}
