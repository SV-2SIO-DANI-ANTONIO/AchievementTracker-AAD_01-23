package com.sanvalero.AchievementTracker.repository;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackerRepository extends CrudRepository<Tracker, Long> {
    List<Tracker> findAll();
    List<Tracker> findByUserAchievement(User user);
    List<Tracker> findByTrackAchievement(Achievement achievement);

}
