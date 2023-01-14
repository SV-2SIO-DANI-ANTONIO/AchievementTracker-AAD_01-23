package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.TrackerDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.TrackerNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.repository.AchievementRepository;
import com.sanvalero.AchievementTracker.repository.TrackerRepository;
import com.sanvalero.AchievementTracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrackerServiceImpl implements TrackerService{

    @Autowired
    TrackerRepository trackerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AchievementRepository achievementRepository;

    private final Logger logger = LoggerFactory.getLogger(TrackerServiceImpl.class);

    @Override
    public List<Tracker> findAll() {
        return trackerRepository.findAll();
    }

    @Override
    public List<Tracker> findByUser(long userId) throws UserNotFoundException {
        logger.info("long userId: " + userId);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return trackerRepository.findByUserAchievement(user);
    }

    @Override
    public List<Tracker> findByAchievement(long achievementId) throws AchievementNotFoundException {
        logger.info("long achievementId: " + achievementId);
        Achievement achievement = achievementRepository.findById(achievementId).orElseThrow(AchievementNotFoundException::new);
        return trackerRepository.findByTrackAchievement(achievement);
    }

    @Override
    public Tracker findById(long id) throws TrackerNotFoundException {
        logger.info("long ID: " + id);
        return trackerRepository.findById(id).orElseThrow(TrackerNotFoundException::new);
    }

    @Override
    public Tracker addTracker(TrackerDTO trackerDTO) throws AchievementNotFoundException, UserNotFoundException {
        logger.info("Tracker ADD: " + trackerDTO);
        Achievement achievement = achievementRepository.findById(trackerDTO.getAchievementId()).orElseThrow(AchievementNotFoundException::new);
        User user = userRepository.findById(trackerDTO.getUserId()).orElseThrow(UserNotFoundException::new);

        Tracker tracker = new Tracker();
        tracker.setAchieved(trackerDTO.isAchieved());
        tracker.setTrackAchievement(achievement);
        tracker.setUserAchievement(user);

        return trackerRepository.save(tracker);
    }

    @Override
    public boolean deleteTracker(long id) throws TrackerNotFoundException {
        Tracker tracker = trackerRepository.findById(id).orElseThrow(TrackerNotFoundException::new);

        trackerRepository.delete(tracker);
        return true;
    }

    @Override
    public Tracker modifyTracker(long id, TrackerDTO trackerDTO) throws TrackerNotFoundException, AchievementNotFoundException, UserNotFoundException {
        Tracker tracker = trackerRepository.findById(id).orElseThrow(TrackerNotFoundException::new);
        Achievement achievement = achievementRepository.findById(trackerDTO.getAchievementId()).orElseThrow(AchievementNotFoundException::new);
        User user = userRepository.findById(trackerDTO.getUserId()).orElseThrow(UserNotFoundException::new);
        logger.info("Old Tracker: " + tracker);
        logger.info("New Tracker: " + trackerDTO);

        tracker.setAchieved(trackerDTO.isAchieved());
        tracker.setUserAchievement(user);
        tracker.setTrackAchievement(achievement);

        return trackerRepository.save(tracker);
    }

    @Override
    public Tracker achieved(long id) throws TrackerNotFoundException {
        Tracker tracker = trackerRepository.findById(id).orElseThrow(TrackerNotFoundException::new);
        tracker.setAchieved(true);
        tracker.setDateAchieved(new Date());
        return tracker;
    }
}
