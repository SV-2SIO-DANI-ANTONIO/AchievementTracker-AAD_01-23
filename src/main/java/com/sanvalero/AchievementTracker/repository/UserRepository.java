package com.sanvalero.AchievementTracker.repository;

import com.sanvalero.AchievementTracker.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findByNameIsContainingIgnoreCase(String name);
}
