package com.sanvalero.AchievementTracker.service;

import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.UserDTO;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findByName(String name);
    User findById(long id) throws UserNotFoundException;
    User addUser(UserDTO userDTO);
    boolean deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, UserDTO userDTO) throws UserNotFoundException;
}
