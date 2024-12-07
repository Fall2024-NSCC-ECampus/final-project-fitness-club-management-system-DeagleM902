package com.example.fitnessclubsystem.controller;

import com.example.fitnessclubsystem.dto.UserRegistrationDto;
import com.example.fitnessclubsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //Display dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    //Display form to add a member
    @GetMapping("/addMember")
    public String showAddMemberForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "admin/add-member";
    }

    //Handle form submission to add a member
    @PostMapping("/addMember")
    public String addMember(@ModelAttribute("user") UserRegistrationDto userDto) {
        userService.createMember(userDto);
        return "redirect:/admin/dashboard";
    }

    //Display form to add a trainer
    @GetMapping("/addTrainer")
    public String showAddTrainerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "admin/add-trainer";
    }

    //Handle form submission to add a trainer
    @PostMapping("/addTrainer")
    public String addTrainer(@ModelAttribute("user") UserRegistrationDto userDto) {
        userService.createTrainer(userDto);
        return "redirect:/admin/dashboard";
    }

    //Manage users page, get members and trainers
    @GetMapping("/manageUsers")
    public String manageUsers(Model model) {
        //Load all users with MEMBER or TRAINER roles
        model.addAttribute("members", userService.getAllMembers());
        model.addAttribute("trainers", userService.getAllTrainers());
        return "admin/manage-users";
    }

    //Endpoint to delete a user by ID
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/manageUsers";
    }
}