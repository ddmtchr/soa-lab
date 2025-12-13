package com.ddmtchr.api.dto.api.filter;

import com.ddmtchr.api.dto.api.FilterOperation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "filter")
@XmlRootElement(name = "filter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterCriteria implements Serializable {

    @NotBlank
    private String field;

    @NotNull
    private FilterOperation op;

    @NotNull
    private String value;
}
