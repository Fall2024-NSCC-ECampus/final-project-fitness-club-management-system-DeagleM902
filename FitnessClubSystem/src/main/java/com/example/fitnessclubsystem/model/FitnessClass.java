package com.example.fitnessclubsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

//The Class entity
@Entity
@Table(name = "fitness_class")
@Data
public class FitnessClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime classTime;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    @ManyToMany
    @JoinTable(name = "fitness_class_members", joinColumns = @JoinColumn(name = "fitness_class_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;
}
