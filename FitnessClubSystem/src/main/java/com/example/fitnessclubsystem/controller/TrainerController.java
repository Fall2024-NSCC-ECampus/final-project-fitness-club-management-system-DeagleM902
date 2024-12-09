package com.example.fitnessclubsystem.controller;

import com.example.fitnessclubsystem.enums.Role;
import com.example.fitnessclubsystem.model.User;
import com.example.fitnessclubsystem.service.ClassService;
import com.example.fitnessclubsystem.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final ClassService classService;
    private final UserService userService;

    public TrainerController(ClassService classService, UserService userService) {
        this.classService = classService;
        this.userService = userService;
    }

    @PostMapping("/addMemberToClass")
    public String addMemberToClass(Authentication authentication, @RequestParam("classId") Long classId, @RequestParam("memberId") Long memberId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User trainer = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (trainer.getRole() != Role.TRAINER) {
            return "error/403";
        }

        classService.addMemberToClass(classId, memberId);
        return "redirect:/schedule/trainer";
    }

    //Manage users page, get members and trainers
    @GetMapping("/memberList")
    public String manageUsers(Model model) {
        //Load all users with MEMBER or TRAINER roles
        model.addAttribute("members", userService.getAllMembers());
        return "trainer/member-list";
    }
}
