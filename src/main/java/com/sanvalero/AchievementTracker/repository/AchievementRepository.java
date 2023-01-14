package com.sanvalero.AchievementTracker.repository;

import com.sanvalero.AchievementTracker.domain.Achievement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    List<Achievement> findAll();
    List<Achievement> findByNameIsContainingIgnoreCase(String name);
    @Query(value = "SELECT \"achievement\".\"id\", \"achievement\".\"description\", \"achievement\".\"name\", \"achievement\".\"game_id\" FROM \"achievement\" \n" +
            "INNER JOIN \"tracker\" AS \"ul1\" ON \"achievement\".\"id\" = \"ul1\".\"achievement_id\"\n" +
            "INNER JOIN \"tracker\" AS \"ul2\" ON \"achievement\".\"id\" = \"ul2\".\"achievement_id\"\n" +
            "WHERE \"ul1\".\"user_id\" = 1 AND \"ul2\".\"user_id\" = 33\n" +
            "GROUP BY \"achievement\".\"id\"", nativeQuery = true)
    List<Achievement> sameAchievement(long userId1, long userId2);

}
