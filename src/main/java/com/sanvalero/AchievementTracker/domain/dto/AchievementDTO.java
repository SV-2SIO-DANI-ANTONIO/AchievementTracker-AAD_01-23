package com.sanvalero.AchievementTracker.domain.dto;

import com.sanvalero.AchievementTracker.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDTO {

    @NotNull(message = "El nombre es obligatoria")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar en blanco")
    private String description;

    @NotNull(message = "El ID es obligatorio")
    private long achievementGameId;
}
