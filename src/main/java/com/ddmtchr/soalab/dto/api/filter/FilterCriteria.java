package com.ddmtchr.soalab.dto.api.filter;

import com.ddmtchr.soalab.dto.api.FilterOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "filter")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Filter")
@XmlRootElement(name = "filter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterCriteria {

    @NotBlank
    @XmlElement(required = true)
    private String field;

    @NotNull
    @XmlElement(required = true)
    private FilterOperation op;

    @NotNull
    @XmlElement(required = true)
    private String value;
}
