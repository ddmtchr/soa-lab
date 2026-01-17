package com.ddmtchr.soalab.dto.coordinates;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Coordinates")
@XmlRootElement(name = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoordinatesResponseDto {

    @Max(135)
    @XmlElement
    private float x; //Максимальное значение поля: 135

    @NotNull
    @XmlElement(required = true)
    private Float y; //Поле не может быть null
}
