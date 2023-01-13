package com.sanvalero.AchievementTracker.domain.dto;

import com.sanvalero.AchievementTracker.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDTO {
    private String name;
    private String description;
    private long achievementGameId;
}
