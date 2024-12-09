# Fitness Club Management System

This Fitness Club Management System is a web application built with **Java**, **Spring Boot**, **Spring Data JPA**, **Spring Security**, **Thymeleaf**, **Lombok**, and **MySQL**. It manages different user roles (Admin, Trainer, and Member) and provides features such as class scheduling, user management, and role-based access control.

---
## Features

### Admin
- **Login:** Admins can log in using their credentials.
- **Add Member/Trainer:** Create new accounts for members and trainers.
- **Delete Member/Trainer:** Remove unwanted accounts.
- **Manage Classes:** Create classes for trainers, edit class details, and delete classes as needed.

### Trainer
- **Login:** Trainers can log in using their credentials.
- **View Schedule:** Trainers can see their assigned classes.
- **Assign Members to Classes:** Trainers can add members to the classes they own.

### Member
- **Login:** Members can log in with their credentials.
- **View Schedule:** Members can see the classes they are enrolled in.

---
## Database Schema

- **User**: Stores user credentials and role (ADMIN, TRAINER, MEMBER).
- **FitnessClass**: Represents a class with fields like `name`, `dayOfWeek`, `classTime`, linked to a `trainer` and a list of `members`.
- **Join Tables**:
    - `fitness_class_members`: Many-to-many relationship between `FitnessClass` and `User` (for members). Allows for members to be enrolled in many classes, and classes to have many members.

---
## Accessing the Application
- **Login Page:** ```http://localhost:8080/login```
- **Admin Dashboard:** ```http://localhost:8080/admin/dashboard```
- **Trainer Schedule:** ```http://localhost:8080/schedule/trainer```
- **Member Schedule:** ```http://localhost:8080/schedule/member```