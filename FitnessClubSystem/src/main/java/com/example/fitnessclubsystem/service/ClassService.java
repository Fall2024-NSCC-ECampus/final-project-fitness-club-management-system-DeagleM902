package com.example.fitnessclubsystem.service;

import com.example.fitnessclubsystem.model.FitnessClass;
import com.example.fitnessclubsystem.model.User;
import com.example.fitnessclubsystem.repository.FitnessClassRepository;
import com.example.fitnessclubsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

//Class functionality and logic
@Service
public class ClassService {
    private final FitnessClassRepository fitnessClassRepository;
    private final UserRepository userRepository;

    public ClassService(FitnessClassRepository fitnessClassRepository, UserRepository userRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
        this.userRepository = userRepository;
    }

    //Creates a class with the given parameters
    public FitnessClass createClass(String className, DayOfWeek dayOfWeek, LocalTime time, Long trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        FitnessClass fitnessClass = new FitnessClass();
        fitnessClass.setName(className);
        fitnessClass.setDayOfWeek(dayOfWeek);
        fitnessClass.setClassTime(time);
        fitnessClass.setTrainer(trainer);
        fitnessClass.setMembers(List.of());
        return fitnessClassRepository.save(fitnessClass);
    }

    //Associates a member to a class
    public FitnessClass addMemberToClass(Long classId, Long memberId) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<User> members = fitnessClass.getMembers();
        members.add(member);
        fitnessClass.setMembers(members);
        return fitnessClassRepository.save(fitnessClass);
    }

    public List<FitnessClass> getAllClasses() {
        return fitnessClassRepository.findAll();
    }

    public void deleteClass(Long classId) {
        fitnessClassRepository.deleteById(classId);
    }

    public FitnessClass getClassById(Long classId) {
        return fitnessClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
    }

    //Updates class info with the given parameters
    public FitnessClass updateClass(Long classId, String newName, DayOfWeek dayOfWeek, LocalTime time, Long trainerId) {
        FitnessClass fitnessClass = getClassById(classId);
        fitnessClass.setName(newName);
        fitnessClass.setDayOfWeek(dayOfWeek);
        fitnessClass.setClassTime(time);
        if (trainerId != null) {
            User trainer = userRepository.findById(trainerId)
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
            fitnessClass.setTrainer(trainer);
        }

        return fitnessClassRepository.save(fitnessClass);
    }

    //Returns classes for a given trainer
    public List<FitnessClass> getAllClassesForTrainer(Long trainerId) {
        return fitnessClassRepository.findAll()
                .stream()
                .filter(fc -> fc.getTrainer().getId().equals(trainerId))
                .toList();
    }

    //Returns classes for a given Member
    public List<FitnessClass> getAllClassesForMember(Long memberId) {
        return fitnessClassRepository.findAll()
                .stream()
                .filter(fc -> fc.getMembers().stream().anyMatch(m -> m.getId().equals(memberId)))
                .toList();
    }
}
