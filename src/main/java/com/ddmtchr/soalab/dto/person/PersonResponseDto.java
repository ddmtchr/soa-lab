package com.ddmtchr.soalab.dto.person;

import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonResponseDto {

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

    private TeamResponseDto team;
}
