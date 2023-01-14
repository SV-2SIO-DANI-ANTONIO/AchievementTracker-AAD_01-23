package com.sanvalero.AchievementTracker.repository;

import com.sanvalero.AchievementTracker.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAll();
    List<Game> findByNameIsContainingIgnoreCase(String name);
}
