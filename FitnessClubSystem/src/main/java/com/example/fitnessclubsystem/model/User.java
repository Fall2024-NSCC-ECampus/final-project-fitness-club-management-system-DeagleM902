package com.example.fitnessclubsystem.model;

import com.example.fitnessclubsystem.enums.Role;
import jakarta.persistence.*;
import lombok.*;

//The user entity, roles are given via enum
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true;
}
