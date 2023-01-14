package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.dto.GameDTO;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;
import com.sanvalero.AchievementTracker.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService{
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> findByName(String name) {
        logger.info("String NAME: " + name);
        return gameRepository.findByNameIsContainingIgnoreCase(name);
    }

    @Override
    public Game findById(long id) throws GameNotFoundException {
        logger.info("long ID: " + id);
        return gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
    }

    @Override
    public Game addGame(GameDTO gameDTO) {
        logger.info("Game ADD: " + gameDTO);
        Game game = new Game();

        modelMapper.map(gameDTO, game);
        game.setAchievementList(new ArrayList<>());

        return gameRepository.save(game);
    }

    @Override
    public boolean deleteGame(long id) throws GameNotFoundException {
        Game game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        if(!game.getAchievementList().isEmpty()){
            return false;
        }

        gameRepository.delete(game);
        return true;
    }

    @Override
    public Game modifyGame(long id, GameDTO gameDTO) throws GameNotFoundException {
        Game existingGame = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        logger.info("Old Game: " + existingGame);
        logger.info("New Game: " + gameDTO);
        List< Achievement> achievements = existingGame.getAchievementList();
        modelMapper.map(gameDTO, existingGame);
        existingGame.setId(id);
        existingGame.setAchievementList(achievements);
        return gameRepository.save(existingGame);
    }
}
