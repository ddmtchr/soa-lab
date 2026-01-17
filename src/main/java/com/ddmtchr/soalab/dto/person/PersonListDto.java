package com.ddmtchr.soalab.dto.person;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "persons")
@XmlRootElement(name = "persons")
@XmlType(name = "Persons")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonListDto {

//    @JacksonXmlProperty(localName = "person")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "person", required = true)
    private List<PersonResponseDto> content;
}
