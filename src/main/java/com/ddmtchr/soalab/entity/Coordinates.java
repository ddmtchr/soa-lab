package com.ddmtchr.soalab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    @NotNull
    @Max(135)
    private float x; //Максимальное значение поля: 135

    @Column(name = "y")
    @NotNull
    private Float y; //Поле не может быть null
}
