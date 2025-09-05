package com.ddmtchr.soalab.entity;

import com.ddmtchr.soalab.dto.DragonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "dragon")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dragon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne
    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creationDate", nullable = false)
    @NotNull
    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(name = "age", nullable = false)
    @Positive
    private int age; //Значение поля должно быть больше 0

    @Column(name = "description", nullable = true)
    private String description; //Поле может быть null

    @Column(name = "weight", nullable = false)
    @NotNull
    @Positive
    private Integer weight; //Значение поля должно быть больше 0, Поле не может быть null

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DragonType type; //Поле не может быть null

    @ManyToOne
    private Person killer; //Поле может быть null
}
