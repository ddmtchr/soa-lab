package com.ddmtchr.soalab.dto;

import com.ddmtchr.soalab.entity.Coordinates;
import com.ddmtchr.soalab.entity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonDto {

    @Positive
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private CoordinatesDto coordinates; //Поле не может быть null

    @NotNull
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Positive
    private int age; //Значение поля должно быть больше 0

    private String description; //Поле может быть null

    @NotNull
    @Positive
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    private DragonType type; //Поле не может быть null

    private PersonDto killer; //Поле может быть null
}
