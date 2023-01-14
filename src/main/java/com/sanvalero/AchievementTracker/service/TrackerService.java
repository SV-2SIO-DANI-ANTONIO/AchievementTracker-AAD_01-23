package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.dto.TrackerDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.TrackerNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;

import java.util.List;

public interface TrackerService {
    List<Tracker> findAll();
    List<Tracker> findByUser(long userId) throws UserNotFoundException;
    List<Tracker> findByAchievement(long achievementId) throws AchievementNotFoundException;
    Tracker findById(long id) throws TrackerNotFoundException;
    Tracker addTracker(TrackerDTO trackerDTO) throws AchievementNotFoundException, UserNotFoundException;
    boolean deleteTracker(long id) throws TrackerNotFoundException;
    Tracker modifyTracker(long id, TrackerDTO trackerDTO) throws TrackerNotFoundException, AchievementNotFoundException, UserNotFoundException;
    Tracker achieved(long id) throws TrackerNotFoundException;
}
