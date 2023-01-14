package com.sanvalero.AchievementTracker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "El nombre es obligatoria")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @Column
    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar en blanco")
    private String description;

    @Column
    private String genre;

    @Column
    @Min(value = 0, message = "El precio minimo es 0")
    @NotNull(message = "El precio es obligatorio")
    private float price;

    @Column
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacon maxima es 5")
    @NotNull(message = "La calificacion es obligatoria")
    private int rating;

    @ToString.Exclude
    @OneToMany(mappedBy = "gameAchievement")
    @JsonBackReference(value = "achievementID")
    private List<Achievement> achievementList;

}
