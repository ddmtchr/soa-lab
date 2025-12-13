package com.ddmtchr.api.dto.dragon;

import com.ddmtchr.api.dto.coordinates.CoordinatesRequestDto;
import com.ddmtchr.api.dto.person.PersonResponseDto;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "dragon")
@XmlRootElement(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DragonRequestDto implements Serializable {

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Valid
    @NotNull
    private CoordinatesRequestDto coordinates; //Поле не может быть null

    @Min(1)
    private Integer age; //Значение поля должно быть больше 0

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
