package com.sanvalero.AchievementTracker;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AchievementTrackerConfig {
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
