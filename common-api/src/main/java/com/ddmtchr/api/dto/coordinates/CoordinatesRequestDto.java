package com.ddmtchr.api.dto.coordinates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "coordinates")
@JacksonXmlRootElement(localName = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoordinatesRequestDto implements Serializable {

    @DecimalMax(value = "135")
    private BigDecimal x; //Максимальное значение поля: 135

    @NotNull
    private BigDecimal y; //Поле не может быть null
}
