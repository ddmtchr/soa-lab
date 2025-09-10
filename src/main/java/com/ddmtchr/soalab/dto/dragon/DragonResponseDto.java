package com.ddmtchr.soalab.dto.dragon;

import com.ddmtchr.soalab.dto.coordinates.CoordinatesDto;
import com.ddmtchr.soalab.dto.person.PersonDto;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@JacksonXmlRootElement(localName = "dragon")
@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonResponseDto {

    @Min(1)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private CoordinatesDto coordinates; //Поле не может быть null

    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(1)
    private int age; //Значение поля должно быть больше 0

    private String description; //Поле может быть null

    @NotNull
    @Min(1)
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    private DragonType type; //Поле не может быть null

    private PersonDto killer; //Поле может быть null
}
