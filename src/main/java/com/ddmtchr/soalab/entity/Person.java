package com.ddmtchr.soalab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column(name = "birthday", nullable = true)
    private LocalDate birthday; //Поле может быть null

    @Column(name = "height", nullable = false)
    @Min(1)
    private long height; //Значение поля должно быть больше 0

    @Column(name = "weight", nullable = false)
    @Min(1)
    private double weight; //Значение поля должно быть больше 0

    @Column(name = "passport_id", nullable = true)
    @Size(min = 7, max = 34)
    private String passportID; //Длина строки должна быть не меньше 7, Строка не может быть пустой, Длина строки не должна быть больше 34, Поле может быть null
}
