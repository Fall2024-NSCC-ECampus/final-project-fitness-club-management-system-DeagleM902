package com.example.fitnessclubsystem.service;

import com.example.fitnessclubsystem.dto.UserRegistrationDto;
import com.example.fitnessclubsystem.enums.Role;
import com.example.fitnessclubsystem.model.User;
import com.example.fitnessclubsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createMember(UserRegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.MEMBER);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User createTrainer(UserRegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.TRAINER);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Object getAllMembers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.MEMBER)
                .toList();
    }

    public Object getAllTrainers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.TRAINER)
                .toList();
    }
}