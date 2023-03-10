package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.dto.AchievementDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;

import java.util.List;

public interface AchievementService {
    List<Achievement> findAll();
    List<Achievement> findByName(String name);
    Achievement findById(long id) throws AchievementNotFoundException;
    Achievement addAchievement(AchievementDTO achievementDTO) throws GameNotFoundException;
    boolean deleteAchievement(long id) throws AchievementNotFoundException;
    Achievement updateAchievement(long id, AchievementDTO achievementDTO) throws GameNotFoundException, AchievementNotFoundException;
    List<Achievement> sameAchievement(long idUser1, long idUser2) throws UserNotFoundException;
}
