package com.sanvalero.AchievementTracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    @NotNull(message = "El nombre es obligatoria")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar en blanco")
    private String description;

    private String genre;

    @Min(value = 0, message = "El precio minimo es 0")
    @NotNull(message = "El precio es obligatorio")
    private float price;

    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacon maxima es 5")
    @NotNull(message = "La calificacion es obligatoria")
    private int rating;
}
