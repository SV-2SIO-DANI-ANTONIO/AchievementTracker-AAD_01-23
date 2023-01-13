package com.sanvalero.AchievementTracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name =  "tracker")
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement trackAchievement;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userAchievement;

    @Column
    private Date dateAchieved;

    @Column
    @NotNull
    private boolean achieved;
}
