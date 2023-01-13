package com.sanvalero.AchievementTracker.repository;

import com.sanvalero.AchievementTracker.domain.Achievement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    List<Achievement> findAll();
    List<Achievement> findByNameIsContainingIgnoreCase(String name);

}
