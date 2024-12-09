package com.example.fitnessclubsystem.controller;

import com.example.fitnessclubsystem.enums.Role;
import com.example.fitnessclubsystem.model.FitnessClass;
import com.example.fitnessclubsystem.model.User;
import com.example.fitnessclubsystem.service.ClassService;
import com.example.fitnessclubsystem.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//For displaying Member and Trainer class schedules
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final ClassService classService;
    private final UserService userService;

    public ScheduleController(ClassService classService, UserService userService) {
        this.classService = classService;
        this.userService = userService;
    }

    //Displays the Trainer class schedule
    @GetMapping("/trainer")
    public String trainerSchedule(Authentication authentication, Model model) {
        //Get currently logged-in trainer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User trainer = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (trainer.getRole() != Role.TRAINER) {
            return "error/403";
        }

        List<FitnessClass> classes = classService.getAllClassesForTrainer(trainer.getId());
        model.addAttribute("classes", classes);
        return "schedule/trainer-schedule";
    }

    //Displays the Member class schedule
    @GetMapping("/member")
    public String memberSchedule(Authentication authentication, Model model) {
        //Get currently logged-in member
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User member = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (member.getRole() != Role.MEMBER) {
            return "error/403";
        }

        List<FitnessClass> classes = classService.getAllClassesForMember(member.getId());
        model.addAttribute("classes", classes);
        return "schedule/member-schedule";
    }
}
