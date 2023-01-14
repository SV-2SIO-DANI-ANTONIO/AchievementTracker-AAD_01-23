package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.dto.GameDTO;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;

import java.util.List;

public interface GameService {
    List<Game> findAll();
    List<Game> findByName(String name);
    Game findById(long id) throws GameNotFoundException;
    Game addGame(GameDTO gameDTO);
    boolean deleteGame(long id) throws GameNotFoundException;
    Game modifyGame(long id, GameDTO gameDTO) throws GameNotFoundException;
}
