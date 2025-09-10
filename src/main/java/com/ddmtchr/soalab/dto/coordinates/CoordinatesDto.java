package com.ddmtchr.soalab.dto.coordinates;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoordinatesDto {

    private Long id;

    @Max(135)
    private float x; //Максимальное значение поля: 135

    @NotNull
    private Float y; //Поле не может быть null
}
