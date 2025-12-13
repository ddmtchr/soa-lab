package com.ddmtchr.api.dto.person;

import com.ddmtchr.api.dto.team.TeamResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "person")
@XmlRootElement(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonResponseDto implements Serializable {

    @NotNull
    private Long id;

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    private LocalDate birthday; //Поле может быть null

    @Min(1)
    private long height; //Значение поля должно быть больше 0

    @Min(1)
    private double weight; //Значение поля должно быть больше 0

    @Size(min = 7, max = 34)
    private String passportID; //Длина строки должна быть не меньше 7, Строка не может быть пустой, Длина строки не должна быть больше 34, Поле может быть null

    @Valid
    private TeamResponseDto team;
}
