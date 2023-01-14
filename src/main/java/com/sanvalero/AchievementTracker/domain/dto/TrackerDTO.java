package com.sanvalero.AchievementTracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackerDTO {
    @NotNull(message = "El ID es obligatorio")
    private long achievementId;

    @NotNull(message = "El ID es obligatorio")
    private long userId;

    private boolean achieved;
}
