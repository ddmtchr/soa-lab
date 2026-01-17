package com.ddmtchr.soalab.dto.person;

import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JacksonXmlRootElement(localName = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person")
@XmlRootElement(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonRequestDto {

    @NotBlank
    @XmlElement(required = true)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private LocalDate birthday; //Поле может быть null

    @Min(1)
    @XmlElement
    private Long height; //Значение поля должно быть больше 0

    @Min(1)
    @XmlElement
    private Double weight; //Значение поля должно быть больше 0

    @Size(min = 7, max = 34)
    @XmlElement
    private String passportID; //Длина строки должна быть не меньше 7, Строка не может быть пустой, Длина строки не должна быть больше 34, Поле может быть null

    @Valid
    @XmlElement
    private TeamResponseDto team;
}
