package com.ddmtchr.soalab.dto.coordinates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "coordinates")
@JacksonXmlRootElement(localName = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoordinatesRequestDto {

    @DecimalMax(value = "135")
    private BigDecimal x; //Максимальное значение поля: 135

    @NotNull
    private BigDecimal y; //Поле не может быть null

//    @AssertTrue(message = "Coordinate x value cannot be accurately represented as Float. Consider using a value with less precision")
//    public boolean x() {
//        if (x == null) return true;
//        float floatValue = x.floatValue();
//        return BigDecimal.valueOf(floatValue).compareTo(x) == 0;
//    }
}
