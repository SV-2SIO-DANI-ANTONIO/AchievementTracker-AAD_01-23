package com.sanvalero.AchievementTracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackerDTO {
    private long achievementId;
    private long userId;
    private boolean achieved;
}
