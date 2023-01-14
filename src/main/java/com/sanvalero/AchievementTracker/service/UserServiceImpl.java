package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.UserDTO;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByName(String name) {
        logger.info("String NAME: " + name);
        return userRepository.findByNameIsContainingIgnoreCase(name);
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        logger.info("long ID: " + id);
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        logger.info("User ADD: " + userDTO);
        User user = new User();

        modelMapper.map(userDTO, user);

        user.setTrackerList(new ArrayList<>());
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(!user.getTrackerList().isEmpty()){
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    @Override
    public User modifyUser(long id, UserDTO userDTO) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        logger.info("Old User: " + user);
        logger.info("New user: " + userDTO);

        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());

        return userRepository.save(user);
    }
}
