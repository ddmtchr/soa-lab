package com.ddmtchr.soalab.dto.coordinates;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "coordinates")
@JacksonXmlRootElement(localName = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoordinatesRequestDto {

    @Max(135)
    private float x; //Максимальное значение поля: 135

    @NotNull
    private Float y; //Поле не может быть null
}
