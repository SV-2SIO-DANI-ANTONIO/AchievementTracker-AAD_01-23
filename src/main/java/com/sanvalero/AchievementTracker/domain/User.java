package com.sanvalero.AchievementTracker.domain;


import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @NotNull(message = "La contraseña es obligatoria")
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "userAchievement")
    private List<Tracker> trackerList;




}
