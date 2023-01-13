package com.sanvalero.AchievementTracker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "achievement")
public class Achievement {
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

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameAchievement;

    @ToString.Exclude
    @OneToMany(mappedBy =  "trackAchievement")
    @JsonBackReference(value = "trackerId_tracker")
    private List<Tracker> trackerList;

}
