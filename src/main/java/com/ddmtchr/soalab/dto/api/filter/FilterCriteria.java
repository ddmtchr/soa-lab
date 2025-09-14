package com.ddmtchr.soalab.dto.api.filter;

import com.ddmtchr.soalab.dto.api.FilterOperation;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "filter")
@XmlRootElement(name = "filter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterCriteria {

    @NotBlank
    private String field;

    @NotNull
    private FilterOperation op;

    @NotNull
    private String value;
}
