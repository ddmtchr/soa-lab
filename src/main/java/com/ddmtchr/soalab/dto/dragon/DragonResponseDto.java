package com.ddmtchr.soalab.dto.dragon;

import com.ddmtchr.soalab.dto.coordinates.CoordinatesResponseDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "dragon")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dragon")
@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonResponseDto {

    @NotNull
    @Min(1)
    @XmlElement(required = true)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotBlank
    @XmlElement(required = true)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Valid
    @NotNull
    @XmlElement(required = true)
    private CoordinatesResponseDto coordinates; //Поле не может быть null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    @XmlElement
    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(1)
    @XmlElement
    private int age; //Значение поля должно быть больше 0

    @XmlElement
    private String description; //Поле может быть null

    @NotNull
    @Min(1)
    @XmlElement(required = true)
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    @XmlElement(required = true)
    private DragonType type; //Поле не может быть null

    @Valid
    @XmlElement(name = "killer")
//    @JacksonXmlProperty(localName = "killer")
//    @Schema(name = "killer", implementation = PersonResponseDto.class)
    private PersonResponseDto killer; //Поле может быть null
}
