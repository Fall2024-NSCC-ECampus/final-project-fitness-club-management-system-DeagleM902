package com.example.fitnessclubsystem.controller;

import com.example.fitnessclubsystem.dto.UserRegistrationDto;
import com.example.fitnessclubsystem.model.FitnessClass;
import com.example.fitnessclubsystem.service.ClassService;
import com.example.fitnessclubsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ClassService classService;

    public AdminController(UserService userService, ClassService classService) {
        this.userService = userService;
        this.classService = classService;
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

    //Retrieve the class creation page
    @GetMapping("/createClassForTrainer")
    public String createClassForTrainer(Model model) {
        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("fitnessClass", new FitnessClass());
        return "admin/create-class";
    }

    //Create a class for a given trainer
    @PostMapping("/createClassForTrainer")
    public String createClassForTrainer(@RequestParam("className") String className,
                                        @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
                                        @RequestParam("classTime") LocalTime classTime,
                                        @RequestParam("trainerId") Long trainerId) {
        classService.createClass(className, dayOfWeek, classTime, trainerId);
        return "redirect:/admin/dashboard";
    }

    //Display all classes
    @GetMapping("/manageClasses")
    public String manageClasses(Model model) {
        List<FitnessClass> classes = classService.getAllClasses();
        model.addAttribute("classes", classes);
        return "admin/manage-classes";
    }

    @PostMapping("/deleteClass")
    public String deleteClass(@RequestParam("classId") Long classId) {
        classService.deleteClass(classId);
        return "redirect:/admin/manageClasses";
    }

    //Show edit class form
    @GetMapping("/editClass")
    public String showEditClassForm(@RequestParam("classId") Long classId, Model model) {
        FitnessClass fitnessClass = classService.getClassById(classId);
        model.addAttribute("fitnessClass", fitnessClass);
        model.addAttribute("daysOfWeek", DayOfWeek.values());

        return "admin/edit-class";
    }

    //Handle form submission to update class
    @PostMapping("/editClass")
    public String editClass(@RequestParam("classId") Long classId,
                            @RequestParam("className") String className,
                            @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
                            @RequestParam("time") LocalTime time,
                            @RequestParam(value = "trainerId", required = false) Long trainerId) {
        classService.updateClass(classId, className, dayOfWeek, time, trainerId);
        return "redirect:/admin/manageClasses";
    }
}