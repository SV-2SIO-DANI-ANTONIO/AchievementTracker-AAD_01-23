package com.sanvalero.AchievementTracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private String name;
    private String description;
    private String genre;
    private float price;
    private int rating;
}
