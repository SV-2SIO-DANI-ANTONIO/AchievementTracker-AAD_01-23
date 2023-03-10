package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.AchievementDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.repository.AchievementRepository;
import com.sanvalero.AchievementTracker.repository.GameRepository;
import com.sanvalero.AchievementTracker.repository.UserRepository;
import org.aspectj.weaver.tools.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService{

    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(AchievementServiceImpl.class);


    @Override
    public List<Achievement> findAll() {
        return achievementRepository.findAll();
    }

    @Override
    public List<Achievement> findByName(String name) {
        logger.info("String NAME: " + name);
        return achievementRepository.findByNameIsContainingIgnoreCase(name);
    }

    @Override
    public Achievement findById(long id) throws AchievementNotFoundException {
        logger.info("Long ID: " + id);
        return achievementRepository.findById(id).orElseThrow(AchievementNotFoundException::new);
    }

    @Override
    public Achievement addAchievement(AchievementDTO achievementDTO) throws GameNotFoundException {
        logger.info("Achievement ADD " + achievementDTO);
        Game game = gameRepository.findById(achievementDTO.getAchievementGameId()).orElseThrow(GameNotFoundException::new);
        Achievement achievement = new Achievement();
        achievement.setGameAchievement(game);
        achievement.setDescription(achievementDTO.getDescription());
        achievement.setName(achievementDTO.getName());
        achievement.setTrackerList(new ArrayList<>());

        return achievementRepository.save(achievement);
    }

    @Override
    public boolean deleteAchievement(long id) throws AchievementNotFoundException {
        Achievement achievement = achievementRepository.findById(id).orElseThrow(AchievementNotFoundException::new);
        if(!achievement.getTrackerList().isEmpty()){
            return false;
        }

        achievementRepository.delete(achievement);
        return true;
    }

    @Override
    public Achievement updateAchievement(long id, AchievementDTO achievementDTO) throws GameNotFoundException, AchievementNotFoundException {
        Achievement achievement = achievementRepository.findById(id).orElseThrow(AchievementNotFoundException::new);
        Game game = gameRepository.findById(achievementDTO.getAchievementGameId()).orElseThrow(GameNotFoundException::new);
        logger.info("Old Achievement: " + achievement);
        logger.info("New Achievement: " + achievementDTO);

        achievement.setName(achievementDTO.getName());
        achievement.setDescription(achievementDTO.getDescription());
        achievement.setGameAchievement(game);

        return achievementRepository.save(achievement);
    }

    @Override
    public List<Achievement> sameAchievement(long idUser1, long idUser2) throws UserNotFoundException {
        User user1 = userRepository.findById(idUser1).orElseThrow(UserNotFoundException::new);
        User user2 = userRepository.findById(idUser2).orElseThrow(UserNotFoundException::new);
        return achievementRepository.sameAchievement(idUser1, idUser2);
    }
}
