package com.ddmtchr.api.dto.dragon;

import com.ddmtchr.api.dto.coordinates.CoordinatesResponseDto;
import com.ddmtchr.api.dto.person.PersonResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "dragon")
@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonResponseDto implements Serializable {

    @NotNull
    @Min(1)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Valid
    @NotNull
    private CoordinatesResponseDto coordinates; //Поле не может быть null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(1)
    private int age; //Значение поля должно быть больше 0

    private String description; //Поле может быть null

    @NotNull
    @Min(1)
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    private DragonType type; //Поле не может быть null

    @Valid
    @XmlElement(name = "killer")
    @JacksonXmlProperty(localName = "killer")
    @Schema(name = "killer", implementation = PersonResponseDto.class)
    private PersonResponseDto killer; //Поле может быть null
}
