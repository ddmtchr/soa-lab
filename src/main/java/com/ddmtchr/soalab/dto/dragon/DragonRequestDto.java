package com.ddmtchr.soalab.dto.dragon;

import com.ddmtchr.soalab.dto.coordinates.CoordinatesResponseDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "dragon")
@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonRequestDto {

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private CoordinatesResponseDto coordinates; //Поле не может быть null

    @Min(1)
    private int age; //Значение поля должно быть больше 0

    private String description; //Поле может быть null

    @NotNull
    @Min(1)
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    private DragonType type; //Поле не может быть null

    private PersonResponseDto killer; //Поле может быть null
}
